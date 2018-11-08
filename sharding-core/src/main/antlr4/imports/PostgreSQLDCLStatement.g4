grammar PostgreSQLDCLStatement;

import PostgreSQLKeyword, Keyword, BaseRule, DataType, Symbol;

createUser
    : CREATE USER roleName (WITH? roleOptions)?
    ;

roleOption
    : SUPERUSER
    | NOSUPERUSER
    | CREATEDB
    | NOCREATEDB
    | CREATEROLE
    | NOCREATEROLE
    | INHERIT
    | NOINHERIT
    | LOGIN
    | NOLOGIN
    | REPLICATION
    | NOREPLICATION
    | BYPASSRLS
    | NOBYPASSRLS
    | CONNECTION LIMIT NUMBER
    | ENCRYPTED? PASSWORD STRING
    | VALID UNTIL STRING
    | IN ROLE roleNames
    | IN GROUP roleNames
    | ROLE roleNames
    | ADMIN roleNames
    | USER roleNames
    | SYSID STRING
    ;

roleOptions
    : roleOption (COMMA roleOption)*
    ;

alterUser
    : ALTER USER roleSpecification WITH roleOptions
    ;

roleSpecification
    : roleName
    | CURRENT_USER
    | SESSION_USER
    ;

renameUser
    : ALTER USER roleName RENAME TO roleName
    ;

alterUserSetConfig
    : ALTER USER (roleSpecification | ALL) (IN DATABASE databaseName)? SET STRING ((TO | EQ) (STRING | ID | NUMBER | DEFAULT) | FROM CURRENT)
    ;

alterUserResetConfig
    : ALTER USER (roleSpecification | ALL) (IN DATABASE databaseName)? RESET (STRING | ALL)
    ;
