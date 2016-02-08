# TheScalaCourse github application (WIP)

[Click here](https://github.com/login/oauth/authorize?scope=user:email&client_id=9eb853c68e2e3a7e7cd2) to authorize the course application. You only need to do this once.

When the course is over, [you can remove it by clicking the "Revoke access" button here](https://github.com/settings/connections/applications/9eb853c68e2e3a7e7cd2).

### Developing and/or Deploying:

If you are hacking on this repo, you can use this command to set the environment variables identifying the github app, before running sbt:
```
source setenv.sh
```

For including the necessary environment variables on a given PaaS, however, you will need to configure them as appropriate for the case of the PaaS at hand. E.g. in heroku need to set them in the admin UI.

To simulate starting the repo on heroku use: (this confirms the heroku Procfile should work)  
```
heroku local web
```

To test by pushing to a repo as a different user:  
http://stackoverflow.com/a/4220493/1509695

Github application currently configured to hit at this server:   
https://github.com/settings/applications/299198

Webhook currently configured to hit at this server:  
https://github.com/organizations/TheScalaCourse/settings/hooks