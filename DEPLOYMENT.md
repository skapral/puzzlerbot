# Puzzler bot

## Deployment options

### In a nutshell

All you need to bootstrap a `@puzzlerbot` private instance is:

1. Your personal cloud host

2. Java 1.8 or higher

3. A Github auth token

4. A `puzzler-app` jar file of latest version.


```

$ mvn dependency:get -DgroupId=com.github.skapral.puzzler -DartifactId=puzzler-app -Dversion=<version number>

$ GITHUB_AUTH_TOKEN=<token> GITLAB_AUTH_TOKEN=<token> java -jar puzzler-app-*.jar

``` 

### Environment variables

| Name                 | Mandatory?  | Description                                                                                |
|----------------------|-------------|--------------------------------------------------------------------------------------------|
| GITHUB_AUTH_TOKEN    | True *      | Valid Github API authentication token, which has at least `repo` permissions.              |
| GITLAB_AUTH_TOKEN    | True **     | Valid Gitlab personal access token, which has at least `api` permissions.                  |
| PORT                 | False       | Port number for HTTP endpoints (5000 by default)                                           |
| GITHUB_HOOK_SECRET   | False       | Github hook secret. If provided, each Github event payload is validated using this secret. |

`*` - Mandatory for using the `@puzzlebot`'s `/github` webhook

`**` - Mandatory for using the `@puzzlebot`'s `/gitlab` webhook


### Deployment from IDE

Just run class `com.github.skapral.puzzler.app.Bootstrap` using your favourite IDE.
Do NOT forget to configure mandatory environment variables.

HINT: For propagating GitHub events from webhooks to your local instance,
use http://www.ultrahook.com/ or alternative.

### Heroku deployment

Puzzlerbot is Heroku-compliant. You may deploy it as a common Heroku application,
just don't forget to configure mandatory environment variables in Heroku
application's dashboard. 
