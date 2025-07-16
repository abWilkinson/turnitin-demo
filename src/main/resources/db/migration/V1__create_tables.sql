CREATE EXTENSION IF NOT EXISTS pg_trgm;

CREATE TABLE athlete (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    birthdate DATE NOT NULL
);
CREATE INDEX idx_athlete_name ON athlete (name);
CREATE INDEX idx_athlete_name_trgm ON athlete USING gin (name gin_trgm_ops);
CREATE INDEX idx_athlete_birthdate ON athlete (birthdate);

CREATE TABLE skill (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL UNIQUE,
    parent_id INTEGER REFERENCES skill(id) ON DELETE SET NULL
);

CREATE INDEX idx_skill_name ON skill (name);
CREATE INDEX idx_skill_parent_id ON skill (parent_id);

CREATE TABLE athlete_skill (
    athlete_id INTEGER REFERENCES athlete(id) ON DELETE CASCADE,
    skill_id INTEGER REFERENCES skill(id) ON DELETE CASCADE,
    PRIMARY KEY (athlete_id, skill_id)
);
CREATE INDEX idx_athlete_skill_athlete_id ON athlete_skill (athlete_id);
CREATE INDEX idx_athlete_skill_skill_id ON athlete_skill (skill_id);

CREATE TABLE championship (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    year INTEGER NOT NULL,
    UNIQUE(name, year)
);
CREATE INDEX idx_championship_year ON championship(year);


CREATE TABLE athlete_championship (
    athlete_id INTEGER REFERENCES athlete(id) ON DELETE CASCADE,
    championship_id INTEGER REFERENCES championship(id) ON DELETE CASCADE,
    PRIMARY KEY (athlete_id, championship_id)
);
CREATE INDEX idx_athlete_championship_athlete_id ON athlete_championship (athlete_id);
CREATE INDEX idx_athlete_championship_championship_id ON athlete_championship (championship_id);