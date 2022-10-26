-- 테스트 계정 관련
-- TODO : 아직 넣지 않는다

-- 카테고리 관련 (26개)
insert into category (name, created_at, created_by, modified_at, modified_by) values
('IT/텔레콤', now(), 'PlatformAdministrator', now(), 'PlatformAdministrator'),
('가정/생활', now(), 'PlatformAdministrator', now(), 'PlatformAdministrator'),
('건강/미용', now(), 'PlatformAdministrator', now(), 'PlatformAdministrator'),
('건축/인테리어', now(), 'PlatformAdministrator', now(), 'PlatformAdministrator'),
('결혼/출산/육아', now(), 'PlatformAdministrator', now(), 'PlatformAdministrator'),
('교육/취업', now(), 'PlatformAdministrator', now(), 'PlatformAdministrator'),
('금융/보험', now(), 'PlatformAdministrator', now(), 'PlatformAdministrator'),
('꽃/이벤트', now(), 'PlatformAdministrator', now(), 'PlatformAdministrator'),
('레저스포츠/취미', now(), 'PlatformAdministrator', now(), 'PlatformAdministrator'),
('문화/미디어', now(), 'PlatformAdministrator', now(), 'PlatformAdministrator'),
('병/의원', now(), 'PlatformAdministrator', now(), 'PlatformAdministrator'),
('건설/부동산', now(), 'PlatformAdministrator', now(), 'PlatformAdministrator'),
('산업기기', now(), 'PlatformAdministrator', now(), 'PlatformAdministrator'),
('성인', now(), 'PlatformAdministrator', now(), 'PlatformAdministrator'),
('식품/음료', now(), 'PlatformAdministrator', now(), 'PlatformAdministrator'),
('여행/교통', now(), 'PlatformAdministrator', now(), 'PlatformAdministrator'),
('의류/패션잡화', now(), 'PlatformAdministrator', now(), 'PlatformAdministrator'),
('인쇄/문구/사무기기', now(), 'PlatformAdministrator', now(), 'PlatformAdministrator'),
('자동차', now(), 'PlatformAdministrator', now(), 'PlatformAdministrator'),
('전문서비스', now(), 'PlatformAdministrator', now(), 'PlatformAdministrator'),
('전자/가전', now(), 'PlatformAdministrator', now(), 'PlatformAdministrator'),
('주류/담배', now(), 'PlatformAdministrator', now(), 'PlatformAdministrator'),
('관공서/단체', now(), 'PlatformAdministrator', now(), 'PlatformAdministrator'),
('게임', now(), 'PlatformAdministrator', now(), 'PlatformAdministrator'),
('유통', now(), 'PlatformAdministrator', now(), 'PlatformAdministrator'),
('기타', now(), 'PlatformAdministrator', now(), 'PlatformAdministrator');

-- 에이전시 계정 관련
insert into agency (agency_id, agency_password, created_at, created_by, modified_at, modified_by, agency_name) values
('TestAgency', '{noop}test1234', now(), '김쾅쾅', now(), '김쾅쾅', '쾅쾅마케팅');

-- 에이전트 그룹 관련 (6개)
insert into agent_group (created_at, created_by, modified_at, modified_by, name, agency_id) values
(now(), 'TestAgency', now(), 'TestAgency', '마케팅 1팀', 'TestAgency'),
(now(), 'TestAgency', now(), 'TestAgency', '마케팅 2팀', 'TestAgency'),
(now(), 'TestAgency', now(), 'TestAgency', '마케팅 3팀', 'TestAgency'),
(now(), 'TestAgency', now(), 'TestAgency', '마케팅 5팀', 'TestAgency'),
(now(), 'TestAgency', now(), 'TestAgency', '마케팅 본부', 'TestAgency'),
(now(), 'TestAgency', now(), 'TestAgency', '빈 그룹', 'TestAgency');

-- 에이전트 관련 (21명)
insert into agent (agent_id, agent_group_id, agency_id, created_at, created_by, modified_at, modified_by, email, nickname, user_password, deleted) values
('agent1', 3, 'TestAgency', '2022-08-04 12:39:20', 'TestAgency', '2021-12-23 23:38:15', 'TestAgency', 'raps0@networkadvertising.org', '하정우', 'PrRdDZvEA', false),
('agent2', 2, 'TestAgency', '2022-07-25 12:02:41', 'TestAgency', '2022-01-05 15:38:29', 'TestAgency', 'mspinke1@diigo.com', '박해수', 'i12tpzJURr6', false),
('agent3', 1, 'TestAgency', '2021-08-23 04:07:58', 'TestAgency', '2021-12-11 18:11:17', 'TestAgency', 'selcum2@zdnet.com', '유현석', 'O5cKP5l', false),
('agent4', 5, 'TestAgency', '2022-04-27 02:32:52', 'TestAgency', '2021-10-18 06:52:03', 'TestAgency', 'kkeitch3@quantcast.com', '조우진', '7JbQ5k6', false),
('agent5', 5, 'TestAgency', '2022-01-27 11:48:15', 'TestAgency', '2021-09-28 07:14:18', 'TestAgency', 'kbarnfather4@pagesperso-orange.fr', '황정민', 'twzXw4mNbwdk', false),
('agent6', 3, 'TestAgency', '2021-08-21 12:02:20', 'TestAgency', '2021-11-05 03:22:28', 'TestAgency', 'fmacpaike5@state.tx.us', '김태리', 'rzVFDnb', false),
('agent7', 4, 'TestAgency', '2022-08-02 04:13:52', 'TestAgency', '2021-12-15 20:55:50', 'TestAgency', 'kibbitt6@qq.com', '김혜수', 'KmepgML59', false),
('agent8', 3, 'TestAgency', '2022-02-03 18:47:57', 'TestAgency', '2022-06-01 01:20:42', 'TestAgency', 'tgooday7@cbsnews.com', '김다미', 'brhwGkbLr4', false),
('agent9', 1, 'TestAgency', '2022-06-08 10:20:31', 'TestAgency', '2022-03-02 20:37:30', 'TestAgency', 'vrenny8@live.com', '진서연', 'ZoZ5muZCjkRB', false),
('agent10', 5, 'TestAgency', '2021-11-11 18:32:16', 'TestAgency', '2022-05-13 20:44:03', 'TestAgency', 'shazlehurst9@hhs.gov', '김효진', '5YgJLgXURBk', false),
('agent11', 3, 'TestAgency', '2021-11-24 08:51:03', 'TestAgency', '2021-09-12 01:01:49', 'TestAgency', 'grositaa@nytimes.com', '유지태', 't2AqxrWayDX', false),
('agent12', 3, 'TestAgency', '2022-06-24 16:43:22', 'TestAgency', '2021-12-07 00:02:38', 'TestAgency', 'tmolfinob@wunderground.com', '전지현', 'TdQTJQ9VGnw4', false),
('agent13', 5, 'TestAgency', '2022-05-13 03:53:55', 'TestAgency', '2021-09-23 10:15:30', 'TestAgency', 'rbalmerc@ovh.net', '이정재', 'n2634Py', false),
('agent14', 1, 'TestAgency', '2022-05-21 07:51:46', 'TestAgency', '2021-08-20 13:08:45', 'TestAgency', 'nkellowayd@google.pl', '마동석', 'Z1sv7Ngyl0ES', false),
('agent15', 5, 'TestAgency', '2021-11-23 13:50:56', 'TestAgency', '2021-09-30 03:01:50', 'TestAgency', 'aivamye@skyrock.com', '손석구', '13nd6tNjfAd', false),
('agent16', 2, 'TestAgency', '2022-05-19 11:15:14', 'TestAgency', '2022-08-08 22:25:34', 'TestAgency', 'jewinf@google.cn', '유인나', 'kRps6w5E60', false),
('agent17', 5, 'TestAgency', '2022-02-09 21:12:38', 'TestAgency', '2021-12-01 10:03:21', 'TestAgency', 'adimmneg@shareasale.com', '전도연', 'B0s4vN8p7G', false),
('agent18', 4, 'TestAgency', '2022-03-14 04:58:46', 'TestAgency', '2022-04-09 22:58:38', 'TestAgency', 'ssmithendh@skype.com', '이지은', 'Uw6uv2vh', false),
('agent19', 5, 'TestAgency', '2021-11-02 20:34:27', 'TestAgency', '2022-06-18 09:16:21', 'TestAgency', 'dlaughreyi@constantcontact.com', '윤계상', 'iW0mlxHrJ4bo', false),
('agent20', 5, 'TestAgency', '2022-04-27 22:31:37', 'TestAgency', '2022-04-28 00:50:10', 'TestAgency', 'hgaleyj@meetup.com', '김윤석', 'BfMqWo0kH', false),
('nullagent', 6, 'TestAgency', '2022-04-27 22:31:37', 'TestAgency', '2022-04-28 00:50:10', 'TestAgency', 'hey@meetup.com', '광고주없음', 'BfMqWo0kH', false);

-- 광고주 관련 (40명)
insert into client_user (client_id, created_at, created_by, modified_at, modified_by, email, nickname, user_password, agency_id, agent_id, category_id) values
('c01', '2022-01-13', 'Molly', '2022-05-16', 'Molly', 'mstabbins0@lycos.com', 'Molly', 'SCLgplR9a', 'TestAgency', 'agent1', 1),
('c02', '2022-03-15', 'Griffith', '2022-07-19', 'Griffith', 'gskeffington1@bigcartel.com', 'Griffith', 'tWoMasu', 'TestAgency', 'agent2', 2),
('c03', '2022-04-08', 'Vladamir', '2022-08-09', 'Vladamir', 'vpedrozzi2@xing.com', 'Vladamir', 'Wwcg2OU59eix', 'TestAgency', 'agent3', 3),
('c04', '2022-01-13', 'Wilton', '2021-11-12', 'Wilton', 'wfould3@elegantthemes.com', 'Wilton', 'qefKXJL1A4ZV', 'TestAgency', 'agent4', 4),
('c05', '2021-11-11', 'Dominga', '2022-03-18', 'Dominga', 'dbilton4@wiley.com', 'Dominga', 'dADQoNdw858', 'TestAgency', 'agent5', 5),
('c06', '2022-06-11', 'Cornelia', '2022-04-05', 'Cornelia', 'ccooley5@princeton.edu', 'Cornelia', 'YcDGsBp', 'TestAgency', 'agent6', 6),
('c07', '2021-10-26', 'Yevette', '2022-06-21', 'Yevette', 'ywreford6@weebly.com', 'Yevette', 'R49RPS8', 'TestAgency', 'agent7', 7),
('c08', '2022-02-26', 'Rozanna', '2021-11-30', 'Rozanna', 'radanet7@ehow.com', 'Rozanna', 'JxcPBD83qQw', 'TestAgency', 'agent8', 8),
('c09', '2022-02-17', 'Michaela', '2022-08-13', 'Michaela', 'mpigott8@slideshare.net', 'Michaela', 'RUYuyOrZ', 'TestAgency', 'agent9', 9),
('c10', '2022-01-08', 'Vincenz', '2021-12-18', 'Vincenz', 'vwhorf9@msu.edu', 'Vincenz', 'SymmLybCU', 'TestAgency', 'agent10', 10),
('c11', '2022-08-15', 'Sukey', '2022-08-02', 'Sukey', 'srobeiroa@godaddy.com', 'Sukey', 'cqVTT4', 'TestAgency', 'agent11', 11),
('c12', '2021-09-23', 'Terza', '2022-06-10', 'Terza', 'twetherillb@tmall.com', 'Terza', 'cs8VhJvxFQ', 'TestAgency', 'agent12', 12),
('c13', '2021-09-21', 'Dora', '2022-01-11', 'Dora', 'dllopc@zdnet.com', 'Dora', 'MIJo5Pd', 'TestAgency', 'agent13', 13),
('c14', '2022-07-10', 'Cecily', '2022-06-20', 'Cecily', 'candrewsd@marriott.com', 'Cecily', 'gGSDHXKEXTE', 'TestAgency', 'agent14', 14),
('c15', '2022-01-22', 'Marilyn', '2022-02-02', 'Marilyn', 'mcoolsone@facebook.com', 'Marilyn', 'gDpz35GGTbm', 'TestAgency', 'agent15', 15),
('c16', '2022-08-14', 'Ashlee', '2021-12-23', 'Ashlee', 'aleytonf@posterous.com', 'Ashlee', 'GhvbFSem', 'TestAgency', 'agent16', 16),
('c17', '2021-12-27', 'Darn', '2022-04-18', 'Darn', 'drapperg@java.com', 'Darn', 'SlCATEw3c', 'TestAgency', 'agent17', 17),
('c18', '2022-04-10', 'Jude', '2022-03-20', 'Jude', 'jlaugah@chronoengine.com', 'Jude', 'Usudxr9HlMf', 'TestAgency', 'agent18', 18),
('c19', '2022-03-05', 'Garald', '2021-12-19', 'Garald', 'gcoultari@paginegialle.it', 'Garald', 'DuCsdj', 'TestAgency', 'agent19', 19),
('c20', '2022-07-11', 'Jeremy', '2022-06-15', 'Jeremy', 'jseinj@msu.edu', 'Jeremy', 'QRa5BuctnVdm', 'TestAgency', 'agent20', 20),
('c21', '2021-10-07', 'Ruth', '2022-02-04', 'Ruth', 'rcurryk@gov.uk', 'Ruth', 'nrdkZAz', 'TestAgency', 'agent1', 21),
('c22', '2022-07-15', 'Yovonnda', '2022-07-03', 'Yovonnda', 'yearpel@businesswire.com', 'Yovonnda', 'czYOfSeuL0', 'TestAgency', 'agent1', 22),
('c23', '2021-11-04', 'Carine', '2022-04-14', 'Carine', 'csteartm@gizmodo.com', 'Carine', 'VEqS7gVVP', 'TestAgency', 'agent1', 23),
('c24', '2022-08-06', 'Billy', '2021-09-27', 'Billy', 'bsealeafn@mozilla.org', 'Billy', 'AZr5RspPlmp', 'TestAgency', 'agent13', 24),
('c25', '2022-05-28', 'Susy', '2021-11-24', 'Susy', 'smathiesono@amazon.co.jp', 'Susy', '5OU6omAqX', 'TestAgency', 'agent20', 25),
('c26', '2022-06-24', 'Pepillo', '2021-09-26', 'Pepillo', 'pfieldhousep@facebook.com', 'Pepillo', 'BN2qZoTaaj', 'TestAgency', 'agent20', 26),
('c27', '2021-12-14', 'Quill', '2022-03-22', 'Quill', 'qedgerq@spotify.com', 'Quill', '9LsQvpT7lsv9', 'TestAgency', 'agent2', 1),
('c28', '2022-03-31', 'Giffard', '2022-04-23', 'Giffard', 'gshenleyr@xinhuanet.com', 'Giffard', 'eEpWxKQ', 'TestAgency', 'agent2', 2),
('c29', '2022-03-23', 'Kania', '2021-12-13', 'Kania', 'kaucoates@forbes.com', 'Kania', 'uPhozO', 'TestAgency', 'agent16', 3),
('c30', '2022-01-22', 'Maryann', '2021-09-17', 'Maryann', 'mlawleet@devhub.com', 'Maryann', 'CWQ1qtQ8f4', 'TestAgency', 'agent10', 4),
('c31', '2021-09-07', 'Jermaine', '2022-06-04', 'Jermaine', 'joxtonu@geocities.jp', 'Jermaine', 'RpwJSL0P', 'TestAgency', 'agent3', 5),
('c32', '2021-10-16', 'Pren', '2022-01-07', 'Pren', 'pgreenov@behance.net', 'Pren', 'JzYygrWu6f', 'TestAgency', 'agent10', 6),
('c33', '2021-08-22', 'Livvy', '2022-01-30', 'Livvy', 'ldionisettow@typepad.com', 'Livvy', '3cQfxBXYZ4b6', 'TestAgency', 'agent7', 7),
('c34', '2021-09-10', 'Ilse', '2022-06-25', 'Ilse', 'imordauntx@wisc.edu', 'Ilse', 'X1D1AiomHm7', 'TestAgency', 'agent10', 8),
('c35', '2022-04-24', 'Robina', '2022-07-30', 'Robina', 'rambrogiy@independent.co.uk', 'Robina', 'iQTJdOJuxv', 'TestAgency', 'agent4', 9),
('c36', '2022-02-28', 'Francklyn', '2022-07-24', 'Francklyn', 'fkenwrickz@mysql.com', 'Francklyn', 'HmCMh0tv6c', 'TestAgency', 'agent13', 10),
('c37', '2021-09-06', 'Susy', '2021-12-05', 'Susy', 'slewsy10@canalblog.com', 'Susy', 'lTB31U', 'TestAgency', 'agent3', 11),
('c38', '2021-11-15', 'Kelly', '2022-02-19', 'Kelly', 'kdamiata11@g.co', 'Kelly', 'RKqWCCXVET', 'TestAgency', 'agent15', 12),
('c39', '2021-11-10', 'Row', '2022-01-19', 'Row', 'rlosty12@weibo.com', 'Row', 'RV5jRsCnw', 'TestAgency', 'agent17', 13),
('c40', '2022-04-03', 'Broddie', '2022-04-27', 'Broddie', 'bfawke13@stanford.edu', 'Broddie', 'p1caCDmyX5', 'TestAgency', 'agent20', 14);

-- 캠페인 관련 (45개)
insert into campaign (client_id, created_at, created_by, modified_at, modified_by, budget, name, activated, deleted) values
('c01', '2022-08-14 13:18:02', 'Yasmin', '2022-04-25 08:47:01', 'Yasmin', 8090, '서울지역캠페인', true, false),
('c01', '2022-03-23 14:56:28', 'Salome', '2021-10-01 16:25:54', 'Salome', 14122, '아이폰추천매장', true, false),
('c01', '2021-09-17 12:33:34', 'Borden', '2022-07-01 01:14:40', 'Borden', 13438, '미사용캠페인1', false, false),
('c01', '2022-06-30 15:35:08', 'Kassi', '2021-10-22 14:57:57', 'Kassi', 18263, '미사용캠페인2', false, false),
('c01', '2021-10-12 13:26:42', 'Alethea', '2021-09-24 01:08:37', 'Alethea', 6243, '미사용캠페인3', false, false),
('c01', '2021-09-09 01:36:42', 'Indira', '2021-09-05 01:23:52', 'Indira', 11358, '미사용캠페인4', false, false),
('c02', '2022-05-22 21:20:16', 'Axel', '2022-06-22 00:44:12', 'Axel', 4327, '2022-05-22 21:20:16, 4327', false, false),
('c03', '2021-10-30 09:15:58', 'Deane', '2022-05-15 22:06:54', 'Deane', 19384, '2021-10-30 09:15:58, 19384', false, false),
('c04', '2022-04-06 19:53:11', 'Theresina', '2022-07-11 10:56:48', 'Theresina', 15381, '2022-04-06 19:53:11, 15381', false, false),
('c05', '2022-06-18 03:50:18', 'Calley', '2022-03-01 00:55:55', 'Calley', 15367, '2022-06-18 03:50:18, 15367', false, false),
('c06', '2021-09-09 03:44:21', 'Menard', '2022-05-16 12:49:59', 'Menard', 8489, '2021-09-09 03:44:21, 8489', false, false),
('c07', '2022-01-27 18:26:31', 'Allen', '2022-03-21 13:12:24', 'Allen', 14765, '2022-01-27 18:26:31, 14765', false, false),
('c08', '2022-05-29 03:51:49', 'Sabra', '2022-06-10 10:58:21', 'Sabra', 6537, '2022-05-29 03:51:49, 6537', false, false),
('c09', '2021-11-12 19:56:26', 'Roderigo', '2022-06-27 04:25:01', 'Roderigo', 11516, '2021-11-12 19:56:26, 11516', false, false),
('c10', '2021-10-09 15:11:07', 'Katleen', '2021-09-30 03:08:02', 'Katleen', 7574, '2021-10-09 15:11:07, 7574', false, false),
('c11', '2022-01-25 21:14:49', 'Isabella', '2022-07-08 23:05:42', 'Isabella', 4196, '2022-01-25 21:14:49, 4196', false, false),
('c12', '2022-03-28 13:45:58', 'Herminia', '2022-04-03 17:27:59', 'Herminia', 12984, '2022-03-28 13:45:58, 12984', false, false),
('c13', '2022-01-06 04:06:48', 'Dexter', '2021-11-03 17:56:46', 'Dexter', 2104, '2022-01-06 04:06:48, 2104', false, false),
('c14', '2022-07-05 05:25:49', 'Pepito', '2022-07-12 14:29:40', 'Pepito', 13967, '2022-07-05 05:25:49, 13967', false, false),
('c15', '2022-05-07 07:00:00', 'Golda', '2022-05-25 18:54:39', 'Golda', 2911, '2022-05-07 07:00:00, 2911', false, false),
('c16', '2021-10-01 14:28:42', 'Seamus', '2021-11-26 22:05:08', 'Seamus', 18701, '2021-10-01 14:28:42, 18701', false, false),
('c17', '2022-01-25 11:51:47', 'Bradley', '2022-01-19 12:17:19', 'Bradley', 15282, '2022-01-25 11:51:47, 15282', false, false),
('c18', '2022-06-22 11:03:32', 'Jedd', '2022-04-05 01:04:43', 'Jedd', 10774, '2022-06-22 11:03:32, 10774', false, false),
('c19', '2022-05-02 17:28:27', 'Peg', '2022-05-16 17:50:10', 'Peg', 16170, '2022-05-02 17:28:27, 16170', false, false),
('c20', '2022-02-14 20:00:02', 'Adelheid', '2022-03-21 15:39:48', 'Adelheid', 6317, '2022-02-14 20:00:02, 6317', false, false),
('c21', '2022-05-22 00:21:44', 'Walsh', '2022-04-10 12:28:50', 'Walsh', 2018, '2022-05-22 00:21:44, 2018', false, false),
('c22', '2022-04-10 18:39:52', 'Susie', '2021-11-15 23:22:59', 'Susie', 4697, '2022-04-10 18:39:52, 4697', false, false),
('c23', '2022-04-23 03:28:37', 'Alexandre', '2021-10-30 07:52:01', 'Alexandre', 5071, '2022-04-23 03:28:37, 5071', false, false),
('c24', '2022-05-05 01:25:28', 'Shell', '2021-11-27 22:40:21', 'Shell', 6491, '2022-05-05 01:25:28, 6491', false, false),
('c25', '2022-06-04 06:35:50', 'Sarene', '2021-09-18 09:36:09', 'Sarene', 15117, '2022-06-04 06:35:50, 15117', false, false),
('c26', '2021-12-12 13:36:26', 'Bailey', '2022-03-25 17:55:50', 'Bailey', 19404, '2021-12-12 13:36:26, 19404', false, false),
('c27', '2021-09-06 20:12:07', 'Warde', '2021-09-12 22:22:49', 'Warde', 10261, '2021-09-06 20:12:07, 10261', false, false),
('c28', '2022-05-14 08:50:00', 'Drucie', '2022-04-27 00:08:57', 'Drucie', 8044, '2022-05-14 08:50:00, 8044', false, false),
('c29', '2021-11-27 09:15:10', 'Ulrich', '2022-02-21 18:09:48', 'Ulrich', 4327, '2021-11-27 09:15:10, 4327', false, false),
('c30', '2022-08-15 01:29:55', 'Maryanne', '2022-02-17 18:45:24', 'Maryanne', 15470, '2022-08-15 01:29:55, 15470', false, false),
('c31', '2022-07-19 16:44:20', 'Ives', '2021-12-21 04:13:20', 'Ives', 18079, '2022-07-19 16:44:20, 18079', false, false),
('c32', '2021-11-17 10:56:30', 'Stanford', '2022-01-23 02:50:40', 'Stanford', 17694, '2021-11-17 10:56:30, 17694', false, false),
('c33', '2021-10-18 21:10:23', 'Rosalyn', '2022-04-27 17:46:35', 'Rosalyn', 12442, '2021-10-18 21:10:23, 12442', false, false),
('c34', '2022-02-08 06:14:21', 'Gussi', '2022-04-01 23:52:56', 'Gussi', 1643, '2022-02-08 06:14:21, 1643', false, false),
('c35', '2021-12-15 09:47:40', 'Hephzibah', '2021-08-28 23:51:42', 'Hephzibah', 17657, '2021-12-15 09:47:40, 17657', false, false),
('c36', '2022-03-27 02:25:16', 'Jeanette', '2022-01-22 21:43:07', 'Jeanette', 19866, '2022-03-27 02:25:16, 19866', false, false),
('c37', '2022-06-24 05:06:04', 'Dionisio', '2022-01-31 15:02:49', 'Dionisio', 5626, '2022-06-24 05:06:04, 5626', false, false),
('c38', '2021-08-25 21:41:45', 'Aristotle', '2021-12-21 15:33:18', 'Aristotle', 1743, '2021-08-25 21:41:45, 1743',  false, false),
('c39', '2022-01-16 10:54:00', 'Bendicty', '2022-06-01 00:37:26', 'Bendicty', 19564, '2022-01-16 10:54:00, 19564', false, false),
('c40', '2021-09-28 20:24:46', 'Griff', '2022-02-01 14:01:53', 'Griff', 17917, '2021-09-28 20:24:46, 17917', false, false);

-- 소재 관련 (14개)
insert into creative (campaign_id, biding_price, keyword, description, url, created_at, created_by, modified_at, modified_by, activated, deleted) values
(1, 7224, '강남핸드폰매장', '핸드폰 구매는 코코볼모바일', 'https://velog.io/@mrcocoball', '2022-07-13 01:32:28', 'Karoline', '2021-10-26 02:46:03', 'Karoline', true, false),
(1, 4503, '서울핸드폰매장', '핸드폰 구매는 코코볼모바일', 'https://velog.io/@mrcocoball', '2022-07-10 04:40:02', 'Ashien', '2022-07-08 09:18:39', 'Ashien', true, false),
(1, 5463, '핸드폰매장추천', '핸드폰 구매는 코코볼모바일', 'https://velog.io/@mrcocoball', '2021-11-26 01:47:21', 'Flossie', '2022-01-29 02:01:15', 'Flossie', false, false),
(1, 8190, '핸드폰중고거래', '핸드폰 구매는 코코볼모바일', 'https://velog.io/@mrcocoball', '2022-06-10 22:12:32', 'Tracey', '2022-07-01 08:11:24', 'Tracey', false, false),
(1, 5423, '핸드폰중고판매', '핸드폰 구매는 코코볼모바일', 'https://velog.io/@mrcocoball', '2022-03-31 01:00:20', 'Anders', '2022-06-08 12:58:18', 'Anders', false, false),
(1, 5423, '중고핸드폰판매', '핸드폰 구매는 코코볼모바일', 'https://velog.io/@mrcocoball', '2022-03-31 01:00:20', 'Anders', '2022-06-08 12:58:18', 'Anders', false, false),
(1, 5423, '핸드폰저렴하게', '핸드폰 구매는 코코볼모바일', 'https://velog.io/@mrcocoball', '2022-03-31 01:00:20', 'Anders', '2022-06-08 12:58:18', 'Anders', false, false),
(1, 5423, '핸드폰견적', '핸드폰 구매는 코코볼모바일', 'https://velog.io/@mrcocoball', '2022-03-31 01:00:20', 'Anders', '2022-06-08 12:58:18', 'Anders', false, false),
(1, 5423, '강남핸드폰견적', '핸드폰 구매는 코코볼모바일', 'https://velog.io/@mrcocoball', '2022-03-31 01:00:20', 'Anders', '2022-06-08 12:58:18', 'Anders', false, false),
(1, 5423, '서울핸드폰견적', '핸드폰 구매는 코코볼모바일', 'https://velog.io/@mrcocoball', '2022-03-31 01:00:20', 'Anders', '2022-06-08 12:58:18', 'Anders', false, false),
(1, 5423, '저가형핸드폰', '핸드폰 구매는 코코볼모바일', 'https://velog.io/@mrcocoball', '2022-03-31 01:00:20', 'Anders', '2022-06-08 12:58:18', 'Anders', false, false),
(2, 7224, '아이폰자급제', '핸드폰 구매는 코코볼모바일', 'https://velog.io/@mrcocoball', '2022-07-13 01:32:28', 'Karoline', '2021-10-26 02:46:03', 'Karoline', true, false),
(2, 4503, '아이폰약정', '핸드폰 구매는 코코볼모바일', 'https://velog.io/@mrcocoball', '2022-07-10 04:40:02', 'Ashien', '2022-07-08 09:18:39', 'Ashien', true, false),
(2, 5463, '아이폰색상추천', '핸드폰 구매는 코코볼모바일', 'https://velog.io/@mrcocoball', '2021-11-26 01:47:21', 'Flossie', '2022-01-29 02:01:15', 'Flossie', false, false);

-- 소재 실적 관련 (1개 소재, 31일치)
insert into performance (creative_id, created_at, view, click, conversion, purchase, spend) values
(1, DATE_SUB(now(), INTERVAL 31 DAY), 669, 95, 36, 5609000, 686280),
(1, DATE_SUB(now(), INTERVAL 30 DAY), 995, 66, 48, 1198000, 476784),
(1, DATE_SUB(now(), INTERVAL 29 DAY), 860, 93, 35, 5471000, 671832),
(1, DATE_SUB(now(), INTERVAL 28 DAY), 442, 66, 24, 7036000, 476784),
(1, DATE_SUB(now(), INTERVAL 27 DAY), 132, 59, 23, 9161000, 426216),
(1, DATE_SUB(now(), INTERVAL 26 DAY), 178, 69, 5, 8862000, 498456),
(1, DATE_SUB(now(), INTERVAL 25 DAY), 468, 74, 10, 6945000, 534576),
(1, DATE_SUB(now(), INTERVAL 24 DAY), 819, 81, 38, 4955000, 585144),
(1, DATE_SUB(now(), INTERVAL 23 DAY), 173, 97, 42, 7167000, 700728),
(1, DATE_SUB(now(), INTERVAL 22 DAY), 223, 74, 14, 9090000, 534576),
(1, DATE_SUB(now(), INTERVAL 21 DAY), 628, 92, 40, 5421000, 664609),
(1, DATE_SUB(now(), INTERVAL 20 DAY), 413, 75, 20, 4095000, 541800),
(1, DATE_SUB(now(), INTERVAL 19 DAY), 993, 55, 46, 6960000, 397320),
(1, DATE_SUB(now(), INTERVAL 18 DAY), 936, 97, 28, 7122000, 700728),
(1, DATE_SUB(now(), INTERVAL 17 DAY), 680, 70, 27, 1464000, 505680),
(1, DATE_SUB(now(), INTERVAL 16 DAY), 352, 52, 3, 5887000, 375648),
(1, DATE_SUB(now(), INTERVAL 15 DAY), 715, 55, 23, 4836000, 397320),
(1, DATE_SUB(now(), INTERVAL 14 DAY), 899, 75, 38, 4750000, 541800),
(1, DATE_SUB(now(), INTERVAL 13 DAY), 694, 94, 39, 8209000, 679056),
(1, DATE_SUB(now(), INTERVAL 12 DAY), 334, 81, 2, 6854000, 585144),
(1, DATE_SUB(now(), INTERVAL 11 DAY), 518, 87, 28, 8596000, 628488),
(1, DATE_SUB(now(), INTERVAL 10 DAY), 504, 75, 16, 7278000, 541800),
(1, DATE_SUB(now(), INTERVAL 9 DAY), 812, 64, 3, 4859000, 462336),
(1, DATE_SUB(now(), INTERVAL 8 DAY), 591, 86, 3, 2713000, 621264),
(1, DATE_SUB(now(), INTERVAL 7 DAY), 552, 63, 23, 1446000, 455112),
(1, DATE_SUB(now(), INTERVAL 6 DAY), 959, 55, 46, 6260000, 397320),
(1, DATE_SUB(now(), INTERVAL 5 DAY), 747, 79, 25, 5307000, 570696),
(1, DATE_SUB(now(), INTERVAL 4 DAY), 152, 51, 26, 8790000, 368424),
(1, DATE_SUB(now(), INTERVAL 3 DAY), 604, 71, 16, 9620000, 512904),
(1, DATE_SUB(now(), INTERVAL 2 DAY), 450, 73, 8, 1249000, 527352),
(1, DATE_SUB(now(), INTERVAL 1 DAY), 535, 97, 34, 7844000, 700728),
(2, DATE_SUB(now(), INTERVAL 31 DAY), 234, 35, 12, 2606000, 545640),
(2, DATE_SUB(now(), INTERVAL 30 DAY), 666, 46, 14, 1128000, 434534),
(2, DATE_SUB(now(), INTERVAL 29 DAY), 554, 23, 15, 4361000, 565232),
(2, DATE_SUB(now(), INTERVAL 28 DAY), 662, 56, 24, 6456000, 365344),
(2, DATE_SUB(now(), INTERVAL 27 DAY), 552, 39, 23, 7621000, 343646),
(2, DATE_SUB(now(), INTERVAL 26 DAY), 17, 49, 5, 7551000, 386536),
(2, DATE_SUB(now(), INTERVAL 25 DAY), 421, 74, 10, 5834000, 487456),
(2, DATE_SUB(now(), INTERVAL 24 DAY), 123, 61, 34, 3435000, 492344),
(2, DATE_SUB(now(), INTERVAL 23 DAY), 155, 16, 12, 6453000, 645348),
(2, DATE_SUB(now(), INTERVAL 22 DAY), 22, 23, 12, 8605000, 436546),
(2, DATE_SUB(now(), INTERVAL 21 DAY), 68, 56, 33, 4325000, 552349),
(2, DATE_SUB(now(), INTERVAL 20 DAY), 43, 34, 20, 3942000, 423420),
(2, DATE_SUB(now(), INTERVAL 19 DAY), 93, 24, 43, 5823000, 295450),
(2, DATE_SUB(now(), INTERVAL 18 DAY), 96, 43, 22, 6033000, 666238),
(2, DATE_SUB(now(), INTERVAL 17 DAY), 68, 65, 27, 1234000, 464530),
(2, DATE_SUB(now(), INTERVAL 16 DAY), 35, 24, 12, 4662000, 324538),
(2, DATE_SUB(now(), INTERVAL 15 DAY), 15, 35, 15, 3645000, 294540),
(2, DATE_SUB(now(), INTERVAL 14 DAY), 99, 65, 30, 3240000, 446530),
(2, DATE_SUB(now(), INTERVAL 13 DAY), 64, 92, 35, 7772000, 545466),
(2, DATE_SUB(now(), INTERVAL 12 DAY), 33, 51, 12, 5734000, 535234),
(2, DATE_SUB(now(), INTERVAL 11 DAY), 51, 56, 28, 7452000, 592348),
(2, DATE_SUB(now(), INTERVAL 10 DAY), 54, 45, 21, 6143000, 454530),
(2, DATE_SUB(now(), INTERVAL 9 DAY), 82, 34, 12, 3645000, 365346),
(2, DATE_SUB(now(), INTERVAL 8 DAY), 91, 56, 10, 1663000, 592434),
(2, DATE_SUB(now(), INTERVAL 7 DAY), 52, 25, 12, 1225000, 395342),
(2, DATE_SUB(now(), INTERVAL 6 DAY), 59, 34, 11, 5104000, 332340),
(2, DATE_SUB(now(), INTERVAL 5 DAY), 47, 56, 21, 4235000, 495346),
(2, DATE_SUB(now(), INTERVAL 4 DAY), 12, 23, 14, 7563000, 293534),
(2, DATE_SUB(now(), INTERVAL 3 DAY), 64, 41, 16, 8534000, 483424),
(2, DATE_SUB(now(), INTERVAL 2 DAY), 45, 54, 8, 945000, 465632),
(2, DATE_SUB(now(), INTERVAL 1 DAY), 55, 55, 34, 5653000, 634428),
(12, DATE_SUB(now(), INTERVAL 13 DAY), 54, 22, 5, 6772000, 445466),
(12, DATE_SUB(now(), INTERVAL 12 DAY), 23, 11, 6, 4734000, 435234),
(12, DATE_SUB(now(), INTERVAL 11 DAY), 41, 36, 14, 6452000, 392348),
(12, DATE_SUB(now(), INTERVAL 10 DAY), 44, 35, 14, 4143000, 254530),
(12, DATE_SUB(now(), INTERVAL 9 DAY), 72, 34, 12, 1645000, 165346),
(12, DATE_SUB(now(), INTERVAL 8 DAY), 81, 36, 10, 663000, 292434),
(12, DATE_SUB(now(), INTERVAL 7 DAY), 42, 25, 6, 225000, 95342),
(12, DATE_SUB(now(), INTERVAL 6 DAY), 49, 24, 8, 4104000, 232340),
(12, DATE_SUB(now(), INTERVAL 5 DAY), 37, 26, 12, 3235000, 395346),
(12, DATE_SUB(now(), INTERVAL 4 DAY), 25, 15, 8, 6563000, 193534),
(12, DATE_SUB(now(), INTERVAL 3 DAY), 54, 31, 12, 3534000, 283424),
(12, DATE_SUB(now(), INTERVAL 2 DAY), 35, 24, 8, 745000, 165632),
(12, DATE_SUB(now(), INTERVAL 1 DAY), 45, 25, 12, 3653000, 334428),
(13, DATE_SUB(now(), INTERVAL 14 DAY), 799, 65, 12, 1750000, 241800),
(13, DATE_SUB(now(), INTERVAL 13 DAY), 594, 64, 29, 2209000, 379056),
(13, DATE_SUB(now(), INTERVAL 12 DAY), 234, 41, 2, 6854000, 285144),
(13, DATE_SUB(now(), INTERVAL 11 DAY), 418, 67, 7, 5596000, 528488),
(13, DATE_SUB(now(), INTERVAL 10 DAY), 404, 35, 1, 4278000, 341800),
(13, DATE_SUB(now(), INTERVAL 9 DAY), 712, 44, 3, 1859000, 262336),
(13, DATE_SUB(now(), INTERVAL 8 DAY), 491, 36, 3, 1713000, 321264),
(13, DATE_SUB(now(), INTERVAL 7 DAY), 452, 53, 21, 446000, 55112),
(13, DATE_SUB(now(), INTERVAL 6 DAY), 859, 25, 12, 1260000, 197320),
(13, DATE_SUB(now(), INTERVAL 5 DAY), 647, 49, 25, 2307000, 470696),
(13, DATE_SUB(now(), INTERVAL 4 DAY), 142, 31, 26, 5790000, 268424),
(13, DATE_SUB(now(), INTERVAL 3 DAY), 504, 21, 16, 5620000, 112904),
(13, DATE_SUB(now(), INTERVAL 2 DAY), 350, 43, 8, 249000, 27352),
(13, DATE_SUB(now(), INTERVAL 1 DAY), 435, 67, 34, 4844000, 200728);