# Google-Sheets-to-Graph

## How to build
Das Projekt kann nicht einfach durch die Eclipsefunktion "Export" erstellt werden. Es muss mit Maven gebaut werden.
Klicke hierzu mit rechtsklick auf die pom.xml und wähle dann "run as" gefolgt von "maven build..." aus. dort setze als Goal "clean install assembly:single".
Das Projekt wird nun als ausführbare .jar in den target-Ordner generiert. Wichtig hierbei ist, dass nur die Version "jar-with-dependencies" ein lauffähiges Projekt beinhaltet.
