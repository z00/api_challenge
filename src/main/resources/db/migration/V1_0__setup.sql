CREATE TABLE IF NOT EXISTS `dogs` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NULL,
  `breedName` varchar(64) NOT NULL,
  `picURL` varchar(512) NOT NULL,
  `picUpVote` int  NULL,
  `picDownVote` int  NULL,
  `voteClientId` varchar(128) NULL,
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8;