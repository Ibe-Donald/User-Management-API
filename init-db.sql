IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'appusersDB')
    BEGIN
        CREATE DATABASE appusersDB;
    END
GO

USE appusersDB;
GO