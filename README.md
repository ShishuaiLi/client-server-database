Self- implemented multi-threaded and synchronized database server/client based on self-customized HTTP protocol. Users can query, insert, delete, and edit key-value data on the server.
Run server first, then run client with IP and port number. In client, use "help" to show the man page, as followed:
help: dispaly the man page.
put (name) (value) (type): add name record to the name service database.
get (name) (type): query a name recod.
del (name) (type): remove a name record from the database.
browse: retrieve all current name records in the database.
exit: terminates the current TCP connection.
