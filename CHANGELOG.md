# Changelog: Phase driver API

What changed in each release of the driver API (`io.github.vojke98:phase-driver-api`). The newest release is at the top.
Format: [Keep a Changelog](https://keepachangelog.com/en/1.1.0/); versions: [Semantic Versioning](https://semver.org/) - a
release that a driver built against an older one would not work with is a MAJOR version, and raises `DriverApi.VERSION`.

## [Unreleased]

## [1.1.0]

### Added

- `DataType.STRING`: text, read and written as a `String`. Where a protocol keeps text in a fixed room (a run of Modbus
  registers), `AddressCheck.maxLength()` and `TagInfo.maxLength()` say how many characters fit (`AddressCheck.valid(type,
  writable, maxLength)`); the server refuses a longer write.
- `DriverProvider.checkAddress(address, dataType)`: the address check for a tag of that type, which the tag registry asks. By
  default the same as `checkAddress(address)`; a protocol without text refuses `STRING` here.

Drivers built against 1.0.0 keep working: the old constructors are still there and `DriverApi.VERSION` stays 1.

## [1.0.0]

### Added

- The first release. `DriverProvider` (a protocol: its settings, address rules, browsing) and `Driver` (one device: open, close,
  write, browse), which is `Polled` (the server asks: `read`), `Pushing` (the device reports changes: `monitor`, then
  `DeviceSession.report`) or both. `DeviceSession` (report, lost, refresh), `Sampling`, `ReadResult` with a `StatusCode` (after
  OPC UA's, each with its `Quality`), `WriteResult`, `SettingsSchema`, `AddressCheck`, `TagInfo` and `DataType`.
  `DriverApi.VERSION` is 1.
