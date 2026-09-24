package fr.ascit.darkmoon.jetbrains.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import fr.ascit.darkmoon.client.ClientMode

/**
 * Non-secret plugin configuration. The Pro JWT is NOT stored here — it lives in
 * PasswordSafe (see DarkmoonSecretService). This state is safe to serialize to
 * disk and to include in support bundles.
 */
@State(
    name = "fr.ascit.darkmoon.DarkmoonSettings",
    storages = [Storage("darkmoon.xml")],
)
class DarkmoonSettings : PersistentStateComponent<DarkmoonSettings.State> {

    data class State(
        /** "auto" | "oss" | "pro" */
        var mode: String = ClientMode.AUTO.wire,
        /** Pro REST base URL, e.g. https://darkmoon.example.com. Empty for OSS. */
        var baseUrl: String = "",
        /**
         * The darkmoon-ci command. A single token is looked up on PATH; multiple
         * tokens allow e.g. "node /opt/darkmoon/cli.cjs". Split on whitespace.
         */
        var cliCommand: String = "darkmoon-ci",
        /** Optional working directory (OSS settings root). Empty = inherit. */
        var workingDir: String = "",
    )

    private var state = State()

    override fun getState(): State = state
    override fun loadState(s: State) { state = s }

    var mode: ClientMode
        get() = when (state.mode.lowercase()) {
            "oss" -> ClientMode.OSS
            "pro" -> ClientMode.PRO
            else -> ClientMode.AUTO
        }
        set(value) { state.mode = value.wire }

    var baseUrl: String
        get() = state.baseUrl
        set(value) { state.baseUrl = value.trim() }

    var cliCommand: String
        get() = state.cliCommand
        set(value) { state.cliCommand = value.trim() }

    var workingDir: String
        get() = state.workingDir
        set(value) { state.workingDir = value.trim() }

    fun commandTokens(): List<String> =
        cliCommand.trim().split(Regex("\\s+")).filter { it.isNotBlank() }
            .ifEmpty { listOf("darkmoon-ci") }

    companion object {
        fun getInstance(): DarkmoonSettings =
            ApplicationManager.getApplication().getService(DarkmoonSettings::class.java)
    }
}
