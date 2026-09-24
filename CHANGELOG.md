# Changelog

## [Unreleased]

### Added

- Initial Darkmoon JetBrains plugin (IntelliJ Platform Gradle Plugin 2.x).
- `darkmoon-client-kotlin` (`:client`): Kotlin port of the frozen `@darkmoon/client`
  v1.0.0 contract, driving the CLI/bridge over a subprocess and validated against
  the shared conformance goldens and fixtures.
- "Darkmoon" tool window: Campaigns, Vulnerabilities (sortable/filterable/
  searchable table + finding detail), and redaction-safe Reports.
- Refresh and Launch-campaign actions.
- Pro JWT stored in PasswordSafe (off the EDT); `mode` / base-URL / CLI-command
  settings under Settings > Tools > Darkmoon.
- Capability-based degradation: Pro-only features disabled on OSS. No
  infrastructure graph and no auto-PR.
- `darkmoon-bridge.mjs`: exposes the full `@darkmoon/client` read surface to the
  plugin without reimplementing the client.
