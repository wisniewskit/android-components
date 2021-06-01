/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package mozilla.components.compose.browser.toolbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import mozilla.components.browser.state.selector.selectedTab
import mozilla.components.browser.state.store.BrowserStore
import mozilla.components.feature.session.SessionUseCases
import mozilla.components.lib.state.ext.observeAsState

/**
 * A customizable toolbar for browsers.
 *
 * The toolbar can switch between two modes: display and edit. The display mode displays the current
 * URL and controls for navigation. In edit mode the current URL can be edited. Those two modes are
 * implemented by the [BrowserDisplayToolbar] and [BrowserEditToolbar] composables.
 */
@Composable
fun BrowserToolbar(
    store: BrowserStore,
    useCases: SessionUseCases
) {
    val url: String? by store.observeAsState { state -> state.selectedTab?.content?.url }
    val editMode = remember { mutableStateOf(false) }

    if (editMode.value) {
        BrowserEditToolbar(
            url = url ?: "<empty>",
            onUrlCommitted = { text ->
                useCases.loadUrl(text)
                editMode.value = false
            }
        )
    } else {
        BrowserDisplayToolbar(
            url = url ?: "<empty>",
            onUrlClicked = { editMode.value = true }
        )
    }
}
