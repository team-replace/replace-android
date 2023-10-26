// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    val agpVersion = "8.0.2"
    id("com.android.application") version agpVersion apply false
    id("com.android.library") version agpVersion apply false

    val kotlinVersion = "1.8.10"
    kotlin("android") version kotlinVersion apply false
    kotlin("jvm") version kotlinVersion apply false

    id("com.google.dagger.hilt.android") version "2.44" apply false
}
