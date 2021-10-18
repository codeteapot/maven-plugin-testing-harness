[![Update](https://github.com/codeteapot/maven-plugin-testing-harness/workflows/Update/badge.svg)](https://github.com/codeteapot/maven-plugin-testing-harness/actions?query=workflow%3AUpdate)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.codeteapot.maven.plugin-testing/maven-plugin-testing-harness?label=Maven%20Central)](https://repo1.maven.org/maven2/com/github/codeteapot/maven/plugin-testing/maven-plugin-testing-harness/)

# Maven Plugin Testing Harness

Mechanisms to manage tests on Mojo.

Visit [project site](https://codeteapot.github.io/maven-plugin-testing-harness/v1.1.2) to see full
documentation.

## Release process (Git based)

First of all, changes related with version should be done here. Such as artifact version, parent,
dependencies, documentation references, and so on.

When they are added to Git index, release commit can be done.

```sh
mvn antrun:run@release-commit
```
It gives a standard way to generate commit descriptions, basing it on the artifact version.

A version is pre-released by creating a version tag, with a standard naming as well.

```sh
mvn antrun:run@release-tag
```

This step is only needed when a release wants to be published.

Both steps could be done at the same time.

```sh
mvn antrun:run@release-commit antrun:run@release-tag
```

## Publishing through GitHub

After creating a release, a draft is created on GitHub. It could be edited and saved in order to
publish its related artifacts.
