# Changelog: Phase driver API

What changed in each release of the driver API (`io.github.vojke98:phase-driver-api`). The newest release is at the top.
Format: [Keep a Changelog](https://keepachangelog.com/en/1.1.0/); versions: [Semantic Versioning](https://semver.org/) - a
release that a driver built against an older one would not work with is a MAJOR version, and raises `DriverApi.VERSION`.

## [Unreleased]

## [1.0.0]

### Added

- The first release: `DriverProvider` (a protocol: its settings, address rules, browsing) and `DeviceDriver` (one device:
  connect, read, write, browse), with `SettingsSchema`, `ReadResult`, `WriteResult`, `AddressCheck`, `TagInfo`, `DataType` and
  `Quality`. `DriverApi.VERSION` is 1.
