gradle.settingsEvaluated {
	if (JavaVersion.current() < JavaVersion.VERSION_21) {
		throw GradleException("This build requires JDK 21. It's currently ${JavaVersion.current()}.")
	}
}

pluginManagement {
	repositories {
		maven("https://mirrors.cloud.tencent.com/nexus/repository/maven-public/")
		maven("https://maven.aliyun.com/repository/public")
		gradlePluginPortal()
		mavenCentral()
		google()
	}
}

dependencyResolutionManagement {
	@Suppress("UnstableApiUsage")
	repositories {
		maven("https://mirrors.cloud.tencent.com/nexus/repository/maven-public/")
		maven("https://maven.aliyun.com/repository/public")
		mavenCentral()
		google()
	}

	versionCatalogs {
		create("libs") {
			from(files("../gradle/libs.versions.toml"))
		}
	}
}