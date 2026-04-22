#!/bin/sh

# Simplified Gradle wrapper launcher script.
# If the wrapper JAR is missing, open the project in Android Studio and run a Gradle sync.

DIR="$(cd "$(dirname "$0")" && pwd)"
JAVA_CMD="${JAVA_HOME:+$JAVA_HOME/bin/}java"
WRAPPER_JAR="$DIR/gradle/wrapper/gradle-wrapper.jar"

if [ ! -f "$WRAPPER_JAR" ]; then
  echo "gradle-wrapper.jar not found. Open the project in Android Studio to regenerate the wrapper."
  exit 1
fi

exec "$JAVA_CMD" -jar "$WRAPPER_JAR" "$@"
