package dependencies

object AndroidX {
    const val core_ktx = "androidx.core:core-ktx:${Versions.core_ktx}"
    const val app_compat = "androidx.appcompat:appcompat:${Versions.app_compat}"
    const val constraint_layout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraint_layout}"
    const val ui_tooling = "androidx.ui:ui-tooling:${Versions.androidx_ui}"

    // we will remove these 2 dependencies later when building the compose-only nav system
    const val nav_fragment_ktx =
        "androidx.navigation:navigation-fragment-ktx:${Versions.nav_component}"
    const val nav_ui_ktx = "androidx.navigation:navigation-ui-ktx:${Versions.nav_component}"

    const val compose_ui = "androidx.compose.ui:ui:${Versions.compose}"
    const val compose_foundation = "androidx.compose.foundation:foundation:${Versions.compose}"
    const val compose_material = "androidx.compose.material:material:${Versions.compose}"
    const val compose_icons_core =
        "androidx.compose.material:material-icons-core:${Versions.compose}"
    const val compose_icons_extended =
        "androidx.compose.material:material-icons-extended:${Versions.compose}"
    const val compose_preview = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"

    const val navigation_compose = "androidx.navigation:navigation-compose:${Versions.nav_compose}"
    const val navigation_hilt = "androidx.hilt:hilt-navigation:${Versions.hilt_navigation}"

    const val room_runtime = "androidx.room:room-runtime:${Versions.room}"
    const val room_ktx = "androidx.room:room-ktx:${Versions.room}"

    const val datastore = "androidx.datastore:datastore-preferences:${Versions.datastore}"

    const val hilt_lifecycle_viewmodel =
        "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hilt_lifecycle_viewmodel}"

    const val work_runtime = "androidx.work:work-runtime-ktx:${Versions.work_runtime}"

    const val constraint_compose =
        "androidx.constraintlayout:constraintlayout-compose:${Versions.constraint_compose}"

    const val hilt_lifecycle = "androidx.hilt:hilt-work:${Versions.hilt_lifecycle}"

    const val lifecycle_runtime_ktx =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle_runtime_ktx}"

    const val activity_compose = "androidx.activity:activity-compose:${Versions.activity_compose}"
}
