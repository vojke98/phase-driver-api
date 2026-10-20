# Phase driver API

What a **protocol driver** for [Phase](https://github.com/vojke98/phase) (a SCADA server) implements - and nothing else of the server.
A driver is one jar: the server loads it from its drivers folder at run time, so a new protocol needs no change to Phase itself.

- Coordinates: `io.github.vojke98:phase-driver-api:1.0.0`, package `io.github.vojke98.phase.driver.api`
- No dependencies. Java 21 or newer.
- License: Apache License 2.0 - write and sell drivers of your own as you like.

## Getting it

**Download** the jar (and its sources and documentation) from this repository's
[Releases](https://github.com/vojke98/phase-driver-api/releases) and add it to your project - scope *provided*: the server has it,
a driver never bundles it.

**Or let Maven fetch it.** Every release is also in this repository's `maven` branch, laid out as a Maven repository:

```xml
<repositories>
    <repository>
        <id>phase-driver-api</id>
        <url>https://raw.githubusercontent.com/vojke98/phase-driver-api/maven</url>
    </repository>
</repositories>

<dependency>
    <groupId>io.github.vojke98</groupId>
    <artifactId>phase-driver-api</artifactId>
    <version>1.0.0</version>
    <scope>provided</scope>
</dependency>
```

(Gradle: `maven { url "https://raw.githubusercontent.com/vojke98/phase-driver-api/maven" }` and
`compileOnly "io.github.vojke98:phase-driver-api:1.0.0"`.)

## Writing a driver

A driver implements two interfaces:

- **`DriverProvider`** - one protocol: its id (`A-Z 0-9 _`, e.g. `MYBUS`), a name for people, the JSON Schema of a device's
  settings (built with `SettingsSchema`: `text`, `secret`, `integer`, `choice`, `bool` - the server checks a device's settings
  against it and fills in the defaults, and the web app builds the form from it), `canBrowse()` (its devices can list their tags),
  `addressHelp()` and `checkAddress(address)` (how its tag addresses are written - the tag form shows it, the tag registry refuses a
  typo with it), `apiVersion()` (return `DriverApi.VERSION`) and `create(settings)`.
- **`DeviceDriver`** - one device: `connect`, `disconnect`, `isConnected`, `read(addresses)` with a `ReadResult` per address (a value,
  its quality GOOD/UNCERTAIN/BAD with a reason, optionally the device's own timestamp), `write(values)` with a `WriteResult` per
  address (written, or refused and why), and optionally `browse()` for importing tags. An `IOException` means the device did not
  answer; an address the device does not have, or a value it refuses, is that address's result. The server calls one device from
  one thread at a time.

```java
public class MyBusDriverProvider implements DriverProvider {

    private static final Map<String, Object> SCHEMA = SettingsSchema.object()
            .text("host", "IP address of the device", true)
            .integer("port", "TCP port", 1, 65_535, 5000, false)
            .build();

    @Override public int apiVersion() { return DriverApi.VERSION; }
    @Override public String protocol() { return "MYBUS"; }
    @Override public String displayName() { return "My bus"; }
    @Override public Map<String, Object> settingsSchema() { return SCHEMA; }

    @Override
    public DeviceDriver create(Map<String, Object> settings) {
        return new MyBusDriver((String) settings.get("host"), ((Number) settings.get("port")).intValue());
    }
}
```

The provider is named in `src/main/resources/META-INF/services/io.github.vojke98.phase.driver.api.DriverProvider` (one line:
`com.example.MyBusDriverProvider`). The jar:

- carries the libraries the driver uses **inside** it (`maven-shade-plugin`) - each driver gets a class loader of its own, so they
  never clash with the server's or another driver's, whatever their versions. Only the JDK, this API and SLF4J (`org.slf4j:slf4j-api`,
  also *provided*) are shared;
- has `Implementation-Version` in its manifest (`maven-jar-plugin`, `addDefaultImplementationEntries`) - the version Phase shows.

The drivers Phase's author maintains are built exactly so; their `pom.xml` is a complete template.

**Installing it:** `sudo phase drivers add my-bus-driver.jar` on a Phase server (or copy the jar into its drivers folder). Phase
loads it within half a minute, without a restart; a newer jar replaces the older one the same way.

A driver runs inside the server with its rights: install only drivers you built or trust.

## Versions

The jar's version follows [Semantic Versioning](https://semver.org/). `DriverApi.VERSION` (now 1) is the API's generation: a
driver says which one it was built for (`apiVersion()`), and a server refuses a driver of another generation - it raises only with
a change that old drivers cannot work with. A driver works with every Phase server of the same generation, whatever their versions.

## Building

```bash
./mvnw verify        # the jar, its sources and documentation in target/
```

Releasing: [CHANGELOG.md](CHANGELOG.md) gets a section for the version, the version goes into `pom.xml`, then the tag
`v<version>` is pushed - GitHub builds it and publishes the release and the `maven` branch (`.github/workflows/release.yml`).
