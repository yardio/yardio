# Yard I/O (aka Yardio)

## The chosen one

This the main project of the Yardio organization. It provides the SBT build based on all other modules using Git submodules. It's the easiest way to either deploy a Yardio server or contribute to the Yardio project.

## Deploy on Heroku

```bash
git clone git@github.com:yardio/yardio.git
cd yardio
git remote add heroku [your heroku git repo]
git push -u heroku master
```

## Contribute

Require SBT 0.13 installed.

```
git clone git@github.com:yardio/yardio.git
cd yardio
git submodule init
git submodule update
sbt
# The following is inside the SBT console
update
compile
project yardio-server-play
run
```

Then, check the running server at [localhost:9000](http://localhost:9000)

## What is...

### a Provider?

It's a communication tool used by organizations to... you know... communicate. Yardio's goal is to enhance such providers by adding notifications from other sources and react to user messages. For example: Slack, HipChat, FlowDock, ...

### an API?

It's a provider endpoint to interact with it, most of the time a REST API. Yardio needs to fully implement it in Scala (well, maybe not fully... but as much as needed).

### a Connector?

It's the way to read and persist data. There can be only one for a running Yardio server. For example, reading from config files, or reading/writing from MongoDB, ...

### a Module?

It's the concrete implementation of an enhancement for providers. For example, integration with JIRA, or GitHub, or SoundCloud, ...
