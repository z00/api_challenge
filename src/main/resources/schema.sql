CREATE TABLE IF NOT EXISTS `dogs` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NULL,
  `breedName` varchar(64) NOT NULL,
  `picURL` varchar(512) NOT NULL,
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `voteinfo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dogId` int not null,
  `picUpVote` int  NULL,
  `picDownVote` int  NULL,
  `voteClientId` varchar(128) NULL,
   FOREIGN KEY (dogId) REFERENCES public.dogs(id),
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8;