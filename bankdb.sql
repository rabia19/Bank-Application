SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `bankdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `accountstbl`
--

CREATE TABLE IF NOT EXISTS `accountstbl` (
  `accountNumber` int(11) NOT NULL AUTO_INCREMENT,
  `customerID` int(11) NOT NULL,
  `accountType` int(11) NOT NULL,
  `sortCode` varchar(8) NOT NULL,
  `interestRate` decimal(4,2) NOT NULL,
  `overdraft` decimal(15,2) NOT NULL,
  `balance` decimal(15,2) NOT NULL,
  PRIMARY KEY (`accountNumber`),
  KEY `accountstbl_ibfk_1` (`customerID`),
  KEY `accountstbl_ibfk_2` (`accountType`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=10000019 ;

--
-- Dumping data for table `accountstbl`
--

INSERT INTO `accountstbl` (`accountNumber`, `customerID`, `accountType`, `sortCode`, `interestRate`, `overdraft`, `balance`) VALUES
(10000000, 2, 2, '12-34-55', '2.50', '0.00', '250.02'),
(10000001, 1, 1, '12-34-45', '30.00', '300.00', '1312.55'),
(10000002, 1, 2, '21-34-12', '2.00', '0.00', '700.04'),
(10000005, 2, 2, '12-65-32', '2.00', '0.00', '1000.05'),
(10000008, 2, 2, '12-65-32', '2.00', '0.00', '320.02'),
(10000009, 2, 1, '12-34-11', '0.00', '122.00', '-374.00'),
(10000010, 4, 2, '12-65-32', '1.00', '0.00', '200.01'),
(10000012, 2, 1, '12-65-32', '0.00', '200.00', '-505.00'),
(10000013, 2, 2, '12-65-32', '3.00', '0.00', '20020.65'),
(10000014, 1, 1, '12-65-32', '0.00', '500.00', '-1405.00'),
(10000015, 1, 2, '12-65-32', '2.00', '0.00', '100.01'),
(10000016, 1, 2, '12-65-32', '1.00', '0.00', '210.01'),
(10000017, 1, 2, '12-65-32', '2.00', '0.00', '1000.05'),
(10000018, 1, 1, '12-65-32', '0.00', '200.00', '-1120.00');

-- --------------------------------------------------------

--
-- Table structure for table `accounttypetbl`
--

CREATE TABLE IF NOT EXISTS `accounttypetbl` (
  `accountTypeID` int(11) NOT NULL AUTO_INCREMENT,
  `accountType` varchar(100) NOT NULL,
  PRIMARY KEY (`accountTypeID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `accounttypetbl`
--

INSERT INTO `accounttypetbl` (`accountTypeID`, `accountType`) VALUES
(1, 'Current Account'),
(2, 'Saving Account');

-- --------------------------------------------------------

--
-- Table structure for table `bankadmins`
--

CREATE TABLE IF NOT EXISTS `bankadmins` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `first_Name` text NOT NULL,
  `last_Name` text NOT NULL,
  `password` text NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `bankadmins`
--

INSERT INTO `bankadmins` (`ID`, `first_Name`, `last_Name`, `password`) VALUES
(1, 'Ieuan', 'Walker', 'password1'),
(2, 'Ryan', 'Payne', 'password2'),
(3, 'David', 'Wall', 'password3'),
(4, 'David', 'Walker', 'password4');

-- --------------------------------------------------------

--
-- Table structure for table `customertbl`
--

CREATE TABLE IF NOT EXISTS `customertbl` (
  `customerID` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(15) NOT NULL,
  `firstName` varchar(100) NOT NULL,
  `lastName` varchar(40) NOT NULL,
  `password` varchar(200) NOT NULL,
  PRIMARY KEY (`customerID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=10 ;

--
-- Dumping data for table `customertbl`
--

INSERT INTO `customertbl` (`customerID`, `title`, `firstName`, `lastName`, `password`) VALUES
(1, 'Mr', 'David', 'Walker', 'pass1'),
(2, 'Mr', 'Adam', 'Morgan', 'pass2'),
(3, 'Mrs', 'Joy', 'Walker', 'pass3'),
(4, 'Mr', 'Angharad', 'Megan', 'pass4'),
(5, 'Mr', 'Ken', 'Schenemum', 'pass5'),
(6, 'Ms', 'Joy', 'Walker', 'pass6'),
(7, 'Mrs', 'Eoin', 'Petty', 'pass7'),
(8, 'Mr', 'Jonh', 'Williams', 'pass8'),
(9, 'Mr', 'Tom', 'Crick', 'pass9');

-- --------------------------------------------------------

--
-- Table structure for table `transactionstbl`
--

CREATE TABLE IF NOT EXISTS `transactionstbl` (
  `transactionID` int(11) NOT NULL AUTO_INCREMENT,
  `accountNumber` int(11) NOT NULL,
  `dateAndTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Description` text,
  `moneyIn` decimal(15,2) DEFAULT NULL,
  `moneyOut` decimal(15,2) DEFAULT NULL,
  `balance` decimal(15,2) NOT NULL,
  PRIMARY KEY (`transactionID`),
  KEY `transactionstbl_ibfk_1` (`accountNumber`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=14 ;

--
-- Dumping data for table `transactionstbl`
--

INSERT INTO `transactionstbl` (`transactionID`, `accountNumber`, `dateAndTime`, `Description`, `moneyIn`, `moneyOut`, `balance`) VALUES
(1, 10000001, '2016-03-27 18:14:49', '', '120.00', '0.00', '1377.00'),
(2, 10000001, '2016-03-27 18:14:58', '', '0.00', '200.00', '1177.00'),
(3, 10000001, '2016-03-27 18:15:10', '', '10.00', '0.00', '1187.00'),
(4, 10000001, '2016-03-27 18:15:18', '', '125.55', '0.00', '1312.55'),
(5, 10000000, '2016-03-27 18:17:39', 'Interest', '0.02', '0.00', '250.02'),
(6, 10000002, '2016-03-27 18:17:39', 'Interest', '0.04', '0.00', '700.04'),
(7, 10000005, '2016-03-27 18:17:39', 'Interest', '0.05', '0.00', '1000.05'),
(8, 10000008, '2016-03-27 18:17:39', 'Interest', '0.02', '0.00', '320.02'),
(9, 10000010, '2016-03-27 18:17:40', 'Interest', '0.01', '0.00', '200.01'),
(10, 10000013, '2016-03-27 18:17:40', 'Interest', '1.65', '0.00', '20020.65'),
(11, 10000015, '2016-03-27 18:17:40', 'Interest', '0.01', '0.00', '100.01'),
(12, 10000016, '2016-03-27 18:17:40', 'Interest', '0.01', '0.00', '210.01'),
(13, 10000017, '2016-03-27 18:17:40', 'Interest', '0.05', '0.00', '1000.05');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `accountstbl`
--
ALTER TABLE `accountstbl`
  ADD CONSTRAINT `accountstbl_ibfk_1` FOREIGN KEY (`customerID`) REFERENCES `customertbl` (`customerID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `accountstbl_ibfk_2` FOREIGN KEY (`accountType`) REFERENCES `accounttypetbl` (`accountTypeID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `transactionstbl`
--
ALTER TABLE `transactionstbl`
  ADD CONSTRAINT `transactionstbl_ibfk_1` FOREIGN KEY (`accountNumber`) REFERENCES `accountstbl` (`accountNumber`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
