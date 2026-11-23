# Changelog: Phase driver API

What changed in each release of the driver API (`io.github.vojke98:phase-driver-api`). The newest release is at the top.
Format: [Keep a Changelog](https://keepachangelog.com/en/1.1.0/); versions: [Semantic Versioning](https://semver.org/) - a
release that a driver built against an older one would not work with is a MAJOR version, and raises `DriverApi.VERSION`.

## [Unreleased]

## [1.0.0]

### Added

- The first release. `DriverProvider` (a protocol: its settings, address rules, browsing) and `Driver` (one device: open, close,
  write, browse), which is `Polled` (the server asks: `read`), `Pushing` (the device reports changes: `monitor`, then
  `DeviceSession.report`) or both. `DeviceSession` (report, lost, refresh), `Sampling`, `ReadResult` with a `StatusCode` (after
  OPC UA's, each with its `Quality`), `WriteResult`, `SettingsSchema`, `AddressCheck`, `TagInfo` and `DataType`.
  `DriverApi.VERSION` is 1.
- `DataType.STRING`: text, read and written as a `String`. Where a protocol keeps text in a fixed room (a run of Modbus
  registers), `AddressCheck.maxLength()` and `TagInfo.maxLength()` say how many characters fit; the server refuses a longer write.
  `DriverProvider.checkAddress(address, dataType)` is the address check for a tag of that type.
- A code a person types for one write: `Driver.write(values, code)` (by default the same as `write(values)`) and
  `WriteResult.codeRequired(message)` - a driver that needs one and was given none says so, the person is asked for it and the write
  is sent again with it (an alarm panel disarmed with the user's own code). Neither the server nor the driver stores or logs it.
