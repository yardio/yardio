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
