MaritimeCloud - A communication framework enabling efficient, secure, reliable and seamless electronic information exchange 
                between all authorized maritime stakeholders across available communication systems.

Last updated 12. December 2013, Copyright 2012 Danish Maritime Authority


Links
-------------------------------------------------------------------------------
Homepage      : http://www.maritimecloud.net (Nothing yet)
Javadoc       : http://www.maritimecloud.net/api
Source        : https://github.com/MaritimeCloud/MaritimeCloud

Issues        : https://github.com/MaritimeCloud/MaritimeCloud/issues
Mailing Lists : http://groups.google.com/group/maritimecloud

Build CI      : https://dma.ci.cloudbees.com/

License       : http://www.apache.org/licenses/LICENSE-2.0.html


Build Instructions
-------------------------------------------------------------------------------
Prerequisites: Java 1.7 + Maven 3.x.x
> git clone https://github.com/MaritimeCloud/MaritimeCloud.git
> cd cake
> mvn install

The build procedure produces a number of distributions.
            
distribution/        
  mc-client-android     A client that can be used on android
  mc-client-jsr356      A client that can be used in any JSR 356 enabled container
  mc-client=standalone  A client that can be used without dependencies
  mc-server             The cloud server

Source Code Organization
-------------------------------------------------------------------------------
The repository is organized into the following components.

mc-core/                The core classes includes definition of Maritime Cloud concepts such as ID

mc-util/                Various utility classes that are used throughout the project.

mc-net/    
  mc-net-api            The client API for communicating between different actors
  mc-net-impl           The default client implementation
  mc-net-messages       The different messages that are send between the clients and servers 
  mc-net-server         The MaritimeCloud server that facilitates communication between actors
  mc-net-server-tck     Contains various end-to-end tests
