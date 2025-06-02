#!/bin/bash

# Fail on any error
set -e

# Install dependencies
apt-get update && \
apt-get install -y curl tar openjdk-17-jdk

# Set Maven version
MAVEN_VERSION=3.9.6

# Download and install Maven
curl -fsSL https://downloads.apache.org/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
  | tar -xz -C /opt

# Create symlinks
ln -s /opt/apache-maven-${MAVEN_VERSION} /opt/maven
ln -s /opt/maven/bin/mvn /usr/bin/mvn

# Print Maven version to confirm
mvn -v
