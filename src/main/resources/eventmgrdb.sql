-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.11.2-MariaDB-1:10.11.2+maria~ubu2204 - mariadb.org binary distribution
-- Server OS:                    debian-linux-gnu
-- HeidiSQL Version:             12.3.0.6589
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for EventMgrDB
DROP DATABASE IF EXISTS `EventMgrDB`;
CREATE DATABASE IF NOT EXISTS `EventMgrDB` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `EventMgrDB`;

-- Dumping structure for table EventMgrDB.Event
DROP TABLE IF EXISTS `Event`;
CREATE TABLE IF NOT EXISTS `Event` (
  `EventID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Name` varchar(100) NOT NULL,
  `Date` date DEFAULT curdate(),
  `Location` varchar(200) DEFAULT NULL,
  `Description` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`EventID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table EventMgrDB.Event: ~1 rows (approximately)
DELETE FROM `Event`;
INSERT INTO `Event` (`EventID`, `Name`, `Date`, `Location`, `Description`) VALUES
	(1, 'DÍszvacsora 2023', '2023-03-05', NULL, NULL);

-- Dumping structure for table EventMgrDB.Organization
DROP TABLE IF EXISTS `Organization`;
CREATE TABLE IF NOT EXISTS `Organization` (
  `OrganizationID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Toplevel` bit(1) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `FK_ParentOrganizationID` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`OrganizationID`),
  KEY `FK_Organization_Organization` (`FK_ParentOrganizationID`),
  CONSTRAINT `FK_Organization_Organization` FOREIGN KEY (`FK_ParentOrganizationID`) REFERENCES `Organization` (`OrganizationID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table EventMgrDB.Organization: ~10 rows (approximately)
DELETE FROM `Organization`;
INSERT INTO `Organization` (`OrganizationID`, `Toplevel`, `Name`, `FK_ParentOrganizationID`) VALUES
	(1, b'1', 'Belügy', NULL),
	(2, b'1', 'Külügy', NULL),
	(3, b'0', 'Külképviseletek', 2),
	(4, b'0', 'Rendőrség', 1),
	(5, b'0', 'ORFK', 4),
	(6, b'0', 'USA', 3),
	(7, b'0', 'Németország', 3),
	(8, b'0', 'Igazgatóság', 5),
	(9, b'1', 'Polgárőrség', NULL),
	(10, b'0', 'Spanyolország', 3);

-- Dumping structure for table EventMgrDB.Person
DROP TABLE IF EXISTS `Person`;
CREATE TABLE IF NOT EXISTS `Person` (
  `PersonID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `FirstName` varchar(100) NOT NULL DEFAULT '',
  `LastName` varchar(100) NOT NULL DEFAULT '',
  `Notes` varchar(2000) DEFAULT NULL COMMENT 'Allergy, pereferences, other - free text',
  `PersonalEmail` varchar(100) DEFAULT NULL COMMENT 'Personal email',
  PRIMARY KEY (`PersonID`),
  FULLTEXT KEY `FirstName` (`FirstName`,`LastName`,`Notes`,`PersonalEmail`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table EventMgrDB.Person: ~4 rows (approximately)
DELETE FROM `Person`;
INSERT INTO `Person` (`PersonID`, `FirstName`, `LastName`, `Notes`, `PersonalEmail`) VALUES
	(1, 'Jakab', 'Gipsz', 'Laktózérzékeny; vegetáriánus', NULL),
	(2, 'Beáta', 'Nagy', 'Nem fogyaszt cukrot', NULL),
	(3, 'Ágnes', 'Kovács', 'Gluténérzékeny', NULL),
	(4, 'Jolán', 'Kittinger', 'Nem fogyaszt halat', 'itt@ott');

-- Dumping structure for table EventMgrDB.PersonToEvent
DROP TABLE IF EXISTS `PersonToEvent`;
CREATE TABLE IF NOT EXISTS `PersonToEvent` (
  `FK_EventID` int(10) unsigned NOT NULL,
  `FK_PersonID` int(10) unsigned NOT NULL,
  `CreatedDate` datetime DEFAULT current_timestamp(),
  KEY `FK_PersonToEvent_Event` (`FK_EventID`),
  KEY `FK_PersonToEvent_Person` (`FK_PersonID`),
  CONSTRAINT `FK_PersonToEvent_Event` FOREIGN KEY (`FK_EventID`) REFERENCES `Event` (`EventID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_PersonToEvent_Person` FOREIGN KEY (`FK_PersonID`) REFERENCES `Person` (`PersonID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table EventMgrDB.PersonToEvent: ~2 rows (approximately)
DELETE FROM `PersonToEvent`;
INSERT INTO `PersonToEvent` (`FK_EventID`, `FK_PersonID`, `CreatedDate`) VALUES
	(1, 3, '2023-03-01 22:21:33'),
	(1, 2, '2023-03-01 21:21:43');

-- Dumping structure for table EventMgrDB.PersonToOrganization
DROP TABLE IF EXISTS `PersonToOrganization`;
CREATE TABLE IF NOT EXISTS `PersonToOrganization` (
  `FK_PersonID` int(10) unsigned NOT NULL,
  `FK_OrganizationID` int(10) unsigned NOT NULL,
  `Role` varchar(100) NOT NULL DEFAULT '',
  `RoleEmail` varchar(100) DEFAULT NULL COMMENT 'Organizational email',
  KEY `FK_PersonToOrganization_Person` (`FK_PersonID`),
  KEY `FK_PersonToOrganization_Organization` (`FK_OrganizationID`),
  CONSTRAINT `FK_PersonToOrganization_Organization` FOREIGN KEY (`FK_OrganizationID`) REFERENCES `Organization` (`OrganizationID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_PersonToOrganization_Person` FOREIGN KEY (`FK_PersonID`) REFERENCES `Person` (`PersonID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table EventMgrDB.PersonToOrganization: ~4 rows (approximately)
DELETE FROM `PersonToOrganization`;
INSERT INTO `PersonToOrganization` (`FK_PersonID`, `FK_OrganizationID`, `Role`, `RoleEmail`) VALUES
	(1, 5, 'Őrmester', NULL),
	(2, 6, 'Nagykövet', NULL),
	(3, 7, 'Titkár', NULL),
	(1, 9, 'Polgárőrparancsnok', NULL);

-- Dumping structure for procedure EventMgrDB.SP_CreateOrganization
DROP PROCEDURE IF EXISTS `SP_CreateOrganization`;
DELIMITER //
CREATE PROCEDURE `SP_CreateOrganization`(
	IN `p_toplevel` bit,
	IN `p_name` varchar(100),
	IN `p_fkparentorgazinationid` int
)
BEGIN
  INSERT INTO Organization (Toplevel, Name, FK_ParentOrganizationID)
  VALUES (p_toplevel, p_name, p_fkparentorgazinationid);
END//
DELIMITER ;

-- Dumping structure for procedure EventMgrDB.SP_CreatePerson
DROP PROCEDURE IF EXISTS `SP_CreatePerson`;
DELIMITER //
CREATE PROCEDURE `SP_CreatePerson`(
    IN p_firstname VARCHAR(100),
    IN p_lastname VARCHAR(100),
    IN p_notes VARCHAR(2000),
    IN p_personalemail VARCHAR(100)
)
BEGIN
  INSERT INTO Person (FirstName, LastName, Notes, PersonalEmail)
  VALUES (p_firstname, p_lastname, p_notes, p_personalemail);
END//
DELIMITER ;

-- Dumping structure for procedure EventMgrDB.SP_RegisterUser
DROP PROCEDURE IF EXISTS `SP_RegisterUser`;
DELIMITER //
CREATE PROCEDURE `SP_RegisterUser`(
    IN p_username VARCHAR(100),
    IN p_password VARCHAR(100),
    OUT p_result VARCHAR(100)
)
BEGIN
    DECLARE username_count INT;
    SELECT COUNT(*) INTO username_count FROM Users WHERE username = p_username;

    IF username_count > 0 THEN
        SET p_result = 'Error: Username already exists';
    ELSE
        INSERT INTO Users (UserName, PasswordHash)
        VALUES (p_username, p_password);
        SET p_result = 'User inserted successfully';
      
    END IF;
END//
DELIMITER ;

-- Dumping structure for procedure EventMgrDB.SP_RelocateOrganization
DROP PROCEDURE IF EXISTS `SP_RelocateOrganization`;
DELIMITER //
CREATE PROCEDURE `SP_RelocateOrganization`(
  IN p_organizationid int unsigned,
  IN p_fkparentorganizationid int unsigned,
  OUT p_result VARCHAR(100)
)
BEGIN
  DECLARE org_count INT;
  DECLARE top_count INT;

  SELECT COUNT(*) INTO org_count FROM Organization WHERE OrganizationID = p_fkparentorganizationid;
  SELECT COUNT(*) INTO top_count FROM Organization WHERE OrganizationID = p_organizationid AND Toplevel = 1;

  IF p_fkparentorganizationid = p_organizationid THEN
    SET p_result = 'Error: Cannot relocate an organization to itself';
  ELSEIF org_count = 0 THEN
    SET p_result = 'Error: Parent organization does not exist';
  ELSEIF top_count > 0 THEN
    SET p_result = 'Error: Cannot relocate a top level organization';
  ELSE
    UPDATE Organization SET FK_ParentOrganizationID=p_fkparentorganizationid
    WHERE Organization.OrganizationID=p_organizationid;
    set p_result = 'Organization relocated successfully';
  END IF;
END//
DELIMITER ;

-- Dumping structure for procedure EventMgrDB.SP_UpdateOrganization
DROP PROCEDURE IF EXISTS `SP_UpdateOrganization`;
DELIMITER //
CREATE PROCEDURE `SP_UpdateOrganization`(
  IN p_organizationid int unsigned,
  IN p_name varchar(100)
)
BEGIN
  UPDATE Organization SET Name=p_name
  WHERE Organization.OrganizationID=p_organizationid;
END//
DELIMITER ;

-- Dumping structure for procedure EventMgrDB.SP_UpdatePerson
DROP PROCEDURE IF EXISTS `SP_UpdatePerson`;
DELIMITER //
CREATE PROCEDURE `SP_UpdatePerson`(
    IN p_personid INT,
	 IN p_firstname VARCHAR(100),
    IN p_lastname VARCHAR(100),
    IN p_notes VARCHAR(2000),
    IN p_personalemail VARCHAR(100)
)
BEGIN
  UPDATE Person SET FirstName=p_firstname, LastName=p_lastname, Notes=p_notes, PersonalEmail=p_personalemail
  WHERE Person.PersonID=p_personid;
END//
DELIMITER ;

-- Dumping structure for table EventMgrDB.Users
DROP TABLE IF EXISTS `Users`;
CREATE TABLE IF NOT EXISTS `Users` (
  `UserID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `UserName` varchar(100) NOT NULL,
  `PasswordHash` varchar(100) NOT NULL,
  `FK_UserTypeID` smallint(5) unsigned NOT NULL DEFAULT 2,
  PRIMARY KEY (`UserID`),
  KEY `fk_Users_Roles` (`FK_UserTypeID`),
  CONSTRAINT `fk_Users_Roles` FOREIGN KEY (`FK_UserTypeID`) REFERENCES `UserType` (`UserTypeID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table EventMgrDB.Users: ~4 rows (approximately)
DELETE FROM `Users`;
INSERT INTO `Users` (`UserID`, `UserName`, `PasswordHash`, `FK_UserTypeID`) VALUES
	(1, 'test', 'password', 2),
	(2, 'test2', 'password2', 2),
	(3, 'test3', 'password2', 2),
	(4, 'test4', 'password2', 2);

-- Dumping structure for table EventMgrDB.UserType
DROP TABLE IF EXISTS `UserType`;
CREATE TABLE IF NOT EXISTS `UserType` (
  `UserTypeID` smallint(5) unsigned NOT NULL,
  `UserTypeName` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`UserTypeID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table EventMgrDB.UserType: ~3 rows (approximately)
DELETE FROM `UserType`;
INSERT INTO `UserType` (`UserTypeID`, `UserTypeName`) VALUES
	(1, 'Admin'),
	(2, 'Felhasználó'),
	(3, 'Kibővített felhasználó');

-- Dumping structure for view EventMgrDB.V_AllPeopleWithRole
DROP VIEW IF EXISTS `V_AllPeopleWithRole`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `V_AllPeopleWithRole` (
	`PersonID` INT(10) UNSIGNED NOT NULL,
	`FirstName` VARCHAR(100) NOT NULL COLLATE 'utf8mb4_general_ci',
	`LastName` VARCHAR(100) NOT NULL COLLATE 'utf8mb4_general_ci',
	`Notes` VARCHAR(2000) NULL COMMENT 'Allergy, pereferences, other - free text' COLLATE 'utf8mb4_general_ci',
	`FK_OrganizationID` INT(10) UNSIGNED NOT NULL,
	`Role` VARCHAR(100) NOT NULL COLLATE 'utf8mb4_general_ci',
	`OrgPath` VARCHAR(403) NOT NULL COLLATE 'utf8mb4_general_ci',
	`OrganizationID` INT(10) UNSIGNED NOT NULL
) ENGINE=MyISAM;

-- Dumping structure for view EventMgrDB.V_ListUsers
DROP VIEW IF EXISTS `V_ListUsers`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `V_ListUsers` (
	`UserID` INT(10) UNSIGNED NOT NULL,
	`UserName` VARCHAR(100) NOT NULL COLLATE 'utf8mb4_general_ci',
	`PasswordHash` VARCHAR(100) NOT NULL COLLATE 'utf8mb4_general_ci',
	`FK_UserTypeID` SMALLINT(5) UNSIGNED NOT NULL,
	`UserTypeName` VARCHAR(100) NULL COLLATE 'utf8mb4_general_ci'
) ENGINE=MyISAM;

-- Dumping structure for view EventMgrDB.V_OrgPath
DROP VIEW IF EXISTS `V_OrgPath`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `V_OrgPath` (
	`OrganizationID` INT(10) UNSIGNED NOT NULL,
	`Name` VARCHAR(100) NOT NULL COLLATE 'utf8mb4_general_ci',
	`OrgPath` VARCHAR(403) NOT NULL COLLATE 'utf8mb4_general_ci'
) ENGINE=MyISAM;

-- Dumping structure for view EventMgrDB.V_PersonDetails
DROP VIEW IF EXISTS `V_PersonDetails`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `V_PersonDetails` (
	`PersonID` INT(10) UNSIGNED NOT NULL,
	`FirstName` VARCHAR(100) NOT NULL COLLATE 'utf8mb4_general_ci',
	`LastName` VARCHAR(100) NOT NULL COLLATE 'utf8mb4_general_ci',
	`Notes` VARCHAR(2000) NULL COMMENT 'Allergy, pereferences, other - free text' COLLATE 'utf8mb4_general_ci',
	`FK_OrganizationID` INT(10) UNSIGNED NOT NULL,
	`Role` VARCHAR(100) NOT NULL COLLATE 'utf8mb4_general_ci'
) ENGINE=MyISAM;

-- Dumping structure for view EventMgrDB.V_ViewPeople
DROP VIEW IF EXISTS `V_ViewPeople`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `V_ViewPeople` (
	`PersonID` INT(10) UNSIGNED NOT NULL,
	`FirstName` VARCHAR(100) NOT NULL COLLATE 'utf8mb4_general_ci',
	`LastName` VARCHAR(100) NOT NULL COLLATE 'utf8mb4_general_ci',
	`Notes` VARCHAR(2000) NULL COMMENT 'Allergy, pereferences, other - free text' COLLATE 'utf8mb4_general_ci',
	`PersonalEmail` VARCHAR(100) NULL COMMENT 'Personal email' COLLATE 'utf8mb4_general_ci'
) ENGINE=MyISAM;

-- Dumping structure for view EventMgrDB.V_AllPeopleWithRole
DROP VIEW IF EXISTS `V_AllPeopleWithRole`;
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `V_AllPeopleWithRole`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `V_AllPeopleWithRole` AS select `PD`.`PersonID` AS `PersonID`,`PD`.`FirstName` AS `FirstName`,`PD`.`LastName` AS `LastName`,`PD`.`Notes` AS `Notes`,`PD`.`FK_OrganizationID` AS `FK_OrganizationID`,`PD`.`Role` AS `Role`,`OP`.`OrgPath` AS `OrgPath`,`OP`.`OrganizationID` AS `OrganizationID` from ((`V_OrgPath` `OP` join `PersonToOrganization` `PO` on(`PO`.`FK_OrganizationID` = `OP`.`OrganizationID`)) join `V_PersonDetails` `PD` on(`PO`.`FK_PersonID` = `PD`.`PersonID` and `PO`.`FK_OrganizationID` = `PD`.`FK_OrganizationID`)) order by 3,2;

-- Dumping structure for view EventMgrDB.V_ListUsers
DROP VIEW IF EXISTS `V_ListUsers`;
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `V_ListUsers`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `V_ListUsers` AS select `u`.`UserID` AS `UserID`,`u`.`UserName` AS `UserName`,`u`.`PasswordHash` AS `PasswordHash`,`u`.`FK_UserTypeID` AS `FK_UserTypeID`,`ut`.`UserTypeName` AS `UserTypeName` from (`Users` `u` join `UserType` `ut` on(`u`.`FK_UserTypeID` = `ut`.`UserTypeID`));

-- Dumping structure for view EventMgrDB.V_OrgPath
DROP VIEW IF EXISTS `V_OrgPath`;
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `V_OrgPath`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `V_OrgPath` AS select `O4`.`OrganizationID` AS `OrganizationID`,`O4`.`Name` AS `Name`,concat(`O1`.`Name`,'/',`O2`.`Name`,'/',`O3`.`Name`,'/',`O4`.`Name`) AS `OrgPath` from (((`Organization` `O1` join `Organization` `O2` on(`O2`.`FK_ParentOrganizationID` = `O1`.`OrganizationID`)) join `Organization` `O3` on(`O3`.`FK_ParentOrganizationID` = `O2`.`OrganizationID`)) join `Organization` `O4` on(`O4`.`FK_ParentOrganizationID` = `O3`.`OrganizationID`)) where `O1`.`Toplevel` = 1 union select `O3`.`OrganizationID` AS `OrganizationID`,`O3`.`Name` AS `Name`,concat(`O1`.`Name`,'/',`O2`.`Name`,'/',`O3`.`Name`) AS `OrgPath` from ((`Organization` `O1` join `Organization` `O2` on(`O2`.`FK_ParentOrganizationID` = `O1`.`OrganizationID`)) join `Organization` `O3` on(`O3`.`FK_ParentOrganizationID` = `O2`.`OrganizationID`)) where `O1`.`Toplevel` = 1 union select `O2`.`OrganizationID` AS `OrganizationID`,`O2`.`Name` AS `Name`,concat(`O1`.`Name`,'/',`O2`.`Name`) AS `OrgPath` from (`Organization` `O1` join `Organization` `O2` on(`O2`.`FK_ParentOrganizationID` = `O1`.`OrganizationID`)) where `O1`.`Toplevel` = 1 union select `O1`.`OrganizationID` AS `OrganizationID`,`O1`.`Name` AS `Name`,concat(`O1`.`Name`) AS `OrgPath` from `Organization` `O1` where `O1`.`Toplevel` = 1 order by 3,1;

-- Dumping structure for view EventMgrDB.V_PersonDetails
DROP VIEW IF EXISTS `V_PersonDetails`;
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `V_PersonDetails`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `V_PersonDetails` AS select `P`.`PersonID` AS `PersonID`,`P`.`FirstName` AS `FirstName`,`P`.`LastName` AS `LastName`,`P`.`Notes` AS `Notes`,`PO`.`FK_OrganizationID` AS `FK_OrganizationID`,`PO`.`Role` AS `Role` from (`Person` `P` join `PersonToOrganization` `PO` on(`P`.`PersonID` = `PO`.`FK_PersonID`)) order by 1;

-- Dumping structure for view EventMgrDB.V_ViewPeople
DROP VIEW IF EXISTS `V_ViewPeople`;
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `V_ViewPeople`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `V_ViewPeople` AS select `Person`.`PersonID` AS `PersonID`,`Person`.`FirstName` AS `FirstName`,`Person`.`LastName` AS `LastName`,`Person`.`Notes` AS `Notes`,`Person`.`PersonalEmail` AS `PersonalEmail` from `Person`;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
