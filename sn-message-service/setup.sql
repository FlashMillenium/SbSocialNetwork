CREATE TABLE `messages` (
  `sender_id` int(11) NOT NULL,
  `recipient_id` int(11) NOT NULL,
  `message` text,
  `sent_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `seen` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`recipient_id`,`sender_id`,`sent_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
