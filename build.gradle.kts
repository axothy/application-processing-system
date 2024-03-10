import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins {
	java
	id("org.springframework.boot") version "3.2.3"
	id("io.spring.dependency-management") version "1.1.4"
	id("org.openapi.generator")  version "7.1.0"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

extra["springCloudVersion"] = "2023.0.0"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.liquibase:liquibase-core")
	implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.modelmapper:modelmapper:3.2.0")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("io.micrometer:micrometer-registry-prometheus")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

	//Security
	implementation("io.jsonwebtoken:jjwt-api:0.12.5")
	//implementation("org.springframework.boot:spring-boot-starter-security")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.5")

}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.register<GenerateTask>("openApiGenerateOperator") {
	generatorName.set("spring")
	validateSpec.set(true)
	inputSpec.set("$projectDir/src/main/resources/application-processing-system-operator-api.yml") // path to spec
	outputDir.set("${layout.buildDirectory.asFile.get()}/generated/java")
	sourceSets.main {
		java.srcDirs("${layout.buildDirectory.asFile.get()}/generated/java")
	}
	apiPackage.set("com.example.applicationprocessingsystem.operator")
	modelPackage.set("com.example.applicationprocessingsystem.operator.model.dto")
	generateApiTests.set(false)
	generateModelTests.set(false)
	generateModelDocumentation.set(false)

	globalProperties.set(
		mapOf(
			"generateSupportingFiles" to "false",
			"models" to "",
			"apis" to "",
		),
	)

	configOptions.set(
		mapOf(
			"documentationProvider" to "none",
			"generatedConstructorWithRequiredArgs" to "true",
			"openApiNullable" to "false",
			"useSpringBoot3" to "true",
			"java8" to "false",
			"skipDefaultInterface" to "true",
			"interfaceOnly" to "true",
			"serviceInterface" to "true",
			"useTags" to "true",
			"fullJavaUtil" to "false",
			"hideGenerationTimestamp" to "true",
			"sourceFolder" to "",
			"library" to "spring-boot",
			"serializationLibrary" to "jackson",
		),
	)
}

tasks.register<GenerateTask>("openApiGenerateAdmin") {
	generatorName.set("spring")
	validateSpec.set(true)
	inputSpec.set("$projectDir/src/main/resources/application-processing-system-admin-api.yml") // path to spec
	outputDir.set("${layout.buildDirectory.asFile.get()}/generated/java")
	sourceSets.main {
		java.srcDirs("${layout.buildDirectory.asFile.get()}/generated/java")
	}
	apiPackage.set("com.example.applicationprocessingsystem.admin")
	modelPackage.set("com.example.applicationprocessingsystem.admin.model.dto")
	generateApiTests.set(false)
	generateModelTests.set(false)
	generateModelDocumentation.set(false)

	globalProperties.set(
		mapOf(
			"generateSupportingFiles" to "false",
			"models" to "",
			"apis" to "",
		),
	)

	configOptions.set(
		mapOf(
			"documentationProvider" to "none",
			"generatedConstructorWithRequiredArgs" to "true",
			"openApiNullable" to "false",
			"useSpringBoot3" to "true",
			"java8" to "false",
			"skipDefaultInterface" to "true",
			"interfaceOnly" to "true",
			"serviceInterface" to "true",
			"useTags" to "true",
			"fullJavaUtil" to "false",
			"hideGenerationTimestamp" to "true",
			"sourceFolder" to "",
			"library" to "spring-boot",
			"serializationLibrary" to "jackson",
		),
	)
}

tasks.register<GenerateTask>("openApiGenerateUser") {
	generatorName.set("spring")
	validateSpec.set(true)
	inputSpec.set("$projectDir/src/main/resources/application-processing-system-user-api.yml") // path to spec
	outputDir.set("${layout.buildDirectory.asFile.get()}/generated/java")
	sourceSets.main {
		java.srcDirs("${layout.buildDirectory.asFile.get()}/generated/java")
	}
	apiPackage.set("com.example.applicationprocessingsystem.user")
	modelPackage.set("com.example.applicationprocessingsystem.user.model.dto")
	generateApiTests.set(false)
	generateModelTests.set(false)
	generateModelDocumentation.set(false)

	globalProperties.set(
		mapOf(
			"generateSupportingFiles" to "false",
			"models" to "",
			"apis" to "",
		),
	)

	configOptions.set(
		mapOf(
			"documentationProvider" to "none",
			"generatedConstructorWithRequiredArgs" to "true",
			"openApiNullable" to "false",
			"useSpringBoot3" to "true",
			"java8" to "false",
			"skipDefaultInterface" to "true",
			"interfaceOnly" to "true",
			"serviceInterface" to "true",
			"useTags" to "true",
			"fullJavaUtil" to "false",
			"hideGenerationTimestamp" to "true",
			"sourceFolder" to "",
			"library" to "spring-boot",
			"serializationLibrary" to "jackson",
		),
	)
}

tasks.withType<JavaCompile> {
	dependsOn("openApiGenerateUser", "openApiGenerateOperator", "openApiGenerateAdmin")
}
