plugins {
    application
    kotlin("jvm") version "1.9.20"
}


tasks {
    sourceSets {
    main {
        kotlin.srcDir("src")
    }
}

    wrapper {
        gradleVersion = "8.5"
    }
}

application {
    mainClass = "Day03Kt"
}

