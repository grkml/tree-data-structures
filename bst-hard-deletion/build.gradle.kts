plugins {
    java
    application
}

repositories {
    jcenter()
}

dependencies {
    implementation("com.google.guava:guava:27.0.1-jre")
}

application {
    mainClassName = "bst_hard_deletion.App"
}
