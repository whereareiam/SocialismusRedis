plugins {
    id("runtime")
}

dependencies {
    implementation(project(":redis-common-api"))
    implementation(project(":redis-common"))
}
