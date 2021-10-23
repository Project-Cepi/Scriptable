# Scriptable
Script editor for Cepi

## How it works

There are 2 main components:

Session (Editor)
Runtime (Scripts)

A session

## Installation

Download the jar from [Releases](https://github.com/Project-Cepi/LuaecEditor/releases)
OR compile it yourself. Instructions to do so are in Compile header

Drop it into the `/extensions` folder.

## Compile

Create a folder, then
Clone the repository using:

`git clone https://github.com/Project-Cepi/LuaecEditor.git`

Once it is cloned, make sure you have gradle installed, and run

`./gradlew shadowJar` on Mac or Linux, and

`gradlew shadowJar` on Windows.

This will output the jar to `build/libs` in the project directory.
