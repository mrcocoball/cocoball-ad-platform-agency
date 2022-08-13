-- 테스트 계정 관련
-- TODO : 아직 넣지 않는다

-- 에이전시 계정 관련
insert into agency (agency_id, created_at, created_by, modified_at, modified_by, agency_name) values
                                                                                                  ('TestAgency', now(), '김쾅쾅', now(), '김쾅쾅', '쾅쾅마케팅');

-- 에이전트 그룹 관련 (3개)
insert into agent_group (agent_group_id, created_at, created_by, modified_at, modified_by, name, agency_id) values
                                                                                                                ('마케팅 1팀', now(), '김쾅쾅', now(), '김쾅쾅', '마케팅 1팀', 'TestAgency'),
                                                                                                                ('마케팅 2팀', now(), '김흐압', now(), '김흐압', '마케팅 2팀', 'TestAgency'),
                                                                                                                ('마케팅 3팀', now(), '김뿅뿅', now(), '김뿅뿅', '마케팅 3팀', 'TestAgency');

-- 에이전트 관련 (5명)
insert into agent (agent_id, created_at, created_by, modified_at, modified_by, email, nickname, user_password, agency_id, agency_group_id) values
                                                                                                                                               ('agent1', now(), '박쾅쾅', now(), '박쾅쾅', 'test1@mail.com', '귀요미1호', 'password01', 'TestAgency', '마케팅 1팀'),
                                                                                                                                               ('agent2', now(), '박흐압', now(), '박흐압', 'test2@mail.com', '귀요미2호', 'password02', 'TestAgency', '마케팅 1팀'),
                                                                                                                                               ('agent3', now(), '박뀽뀽', now(), '박뀽뀽', 'test3@mail.com', '귀요미3호', 'password03', 'TestAgency', '마케팅 2팀'),
                                                                                                                                               ('agent4', now(), '박홍홍', now(), '박홍홍', 'test4@mail.com', '귀요미4호', 'password04', 'TestAgency', '마케팅 2팀'),
                                                                                                                                               ('agent5', now(), '박용용', now(), '박용용', 'test5@mail.com', '귀요미5호', 'password05', 'TestAgency', '마케팅 3팀');

-- 광고주 관련 (7명)
insert into client_user (client_id, created_at, created_by, modified_at, modified_by, email, nickname, user_password, agency_id, agent_id) values
                                                                                                                                               ('client1', now(), '가가가', now(), '가가가', 'gaga@mail.com', '가', '비번1', 'TestAgency', 'agent1'),
                                                                                                                                               ('client2', now(), '나나나', now(), '나나나', 'nana@mail.com', '나', '비번2', 'TestAgency', 'agent1'),
                                                                                                                                               ('client3', now(), '다다다', now(), '다다다', 'dada@mail.com', '다', '비번3', 'TestAgency', 'agent2'),
                                                                                                                                               ('client4', now(), '라라라', now(), '라라라', 'lala@mail.com', '라', '비번4', 'TestAgency', 'agent3'),
                                                                                                                                               ('client5', now(), '마마마', now(), '마마마', 'mama@mail.com', '마', '비번5', 'TestAgency', 'agent4'),
                                                                                                                                               ('client6', now(), '바바바', now(), '바바바', 'baba@mail.com', '바', '비번6', 'TestAgency', 'agent4'),
                                                                                                                                               ('client7', now(), '사사사', now(), '사사사', 'sasa@mail.com', '사', '비번7', 'TestAgency', 'agent5');

-- 캠페인 관련 (5명)
insert into campaign (created_at, created_by, modified_at, modified_by, budget, name, client_id) values (now(), 'aa', now(), 'aa', 10000, 'c1', 'client1'),
                                                                                                                        (now(), 'aa', now(), 'aa', 10000, 'c1', 'client2'),
                                                                                                                        (now(), 'aa', now(), 'aa', 10000, 'c1', 'client3'),
                                                                                                                        (now(), 'aa', now(), 'aa', 10000, 'c2', 'client3'),
                                                                                                                        (now(), 'aa', now(), 'aa', 10000, 'c3', 'client3');

-- 소재 관련 (3개)
insert into creative (created_at, created_by, modified_at, modified_by, biding_price, click, conversion, keyword, view, campaign_id) values (now(), 'aa', now(), 'aa', 1000, 13233, 56, '방배동맛집', 500, 1),
                                                                                                                                                         (now(), 'aa', now(), 'aa', 1000, 13233, 56, '청룡동맛집', 500, 3),
                                                                                                                                                         (now(), 'aa', now(), 'aa', 1000, 13233, 56, '신사동맛집', 500, 4)