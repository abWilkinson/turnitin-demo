-- Parent skills
INSERT INTO skill (id, name, parent_id) VALUES (1, 'Winter sports', NULL);
INSERT INTO skill (id, name, parent_id) VALUES (2, 'Cycling', NULL);

-- Subskills of Winter sports
INSERT INTO skill (id, name, parent_id) VALUES (3, 'Snowboarding', 1);
INSERT INTO skill (id, name, parent_id) VALUES (4, 'Skiing', 1);
INSERT INTO skill (id, name, parent_id) VALUES (5, 'Gymnastics', NULL);
-- Subskills of Snowboarding
INSERT INTO skill (id, name, parent_id) VALUES (6, 'Alpine', 3);

INSERT INTO athlete (id, name, birthdate) VALUES
(1, 'Anna Gasser', '1991-08-16'),
(2, 'Tess Ledeux', '2001-11-23'),
(3, 'Nairo Quintana', '1990-02-04'),
(4, 'John Smith', '2010-01-01'),
(5, 'Homer Simpson', '2005-01-01');

-- Anna Gasser: Winter sports, Snowboarding, Gymnastics
INSERT INTO athlete_skill (athlete_id, skill_id) VALUES
(1, 1),  -- Winter sports
(1, 3),  -- Snowboarding
(1, 5);  -- Gymnastics

-- Tess Ledeux: Skiing
INSERT INTO athlete_skill (athlete_id, skill_id) VALUES
(2, 4);  -- Skiing

-- Nairo Quintana: Cycling
INSERT INTO athlete_skill (athlete_id, skill_id) VALUES
(3, 2);  -- Cycling

-- John Smith: Alpine
INSERT INTO athlete_skill (athlete_id, skill_id) VALUES
(4, 6);  -- Alpine

-- Homer Simpson : Cycling
INSERT INTO athlete_skill (athlete_id, skill_id) VALUES
(5, 2);  -- Cycling

INSERT INTO championship (id, name, year) VALUES
(1, 'World Snowboard Tour', 2010),
(2, 'World Snowboard Tour', 2011),
(3, 'FIS Snowboarding World Championship', 2013),
(4, 'Winter Olympics', 2014),
(5, 'FIS Snowboarding World Championship', 2015),
(6, 'FIS Snowboarding World Championship', 2016),
(7, 'FIS Snowboarding World Championship', 2017),
(8, 'FIS race', 2016),
(9, 'FIS Freestyle World Championship', 2017),
(10, 'Route du Sud', 2012),
(11, 'Tour of the Basque Country', 2013),
(12, 'Tour de France', 2014),
(13, 'Tour de France', 2015),
(14, 'Tour de France', 2016),
(15, 'FIS Snowboarding World Championship', 2025);

-- Anna Gasser
INSERT INTO athlete_championship (athlete_id, championship_id) VALUES
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7);

-- Tess Ledeux
INSERT INTO athlete_championship (athlete_id, championship_id) VALUES
(2, 8), (2, 9);

-- Nairo Quintana
INSERT INTO athlete_championship (athlete_id, championship_id) VALUES
(3, 10), (3, 11), (3, 12), (3, 13), (3, 14);

-- John Smith
INSERT INTO athlete_championship (athlete_id, championship_id) VALUES
(4, 15);