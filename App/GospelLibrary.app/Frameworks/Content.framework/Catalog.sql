CREATE TABLE metadata (
    key TEXT NOT NULL PRIMARY KEY,
    value TEXT NOT NULL
    );
    
CREATE TABLE language (
    id INTEGER NOT NULL PRIMARY KEY,
    lds TEXT NOT NULL,
    iso639_3 TEXT NOT NULL,
    bcp47 TEXT,
    root_library_collection_id INTEGER NOT NULL,
    UNIQUE (lds),
    UNIQUE (iso639_3),
    UNIQUE (root_library_collection_id)
    );
        CREATE INDEX language_bcp47_idx ON language (bcp47);
        CREATE INDEX language_lds_idx ON language (lds);
        CREATE INDEX language_iso639_3_idx ON language (iso639_3);
    
CREATE TABLE language_name (
    language_id INTEGER NOT NULL,
    localization_language_id INTEGER NOT NULL,
    name TEXT NOT NULL,
    PRIMARY KEY (language_id, localization_language_id)
    );

CREATE TABLE library_collection (
    id INTEGER NOT NULL PRIMARY KEY,
    external_id TEXT NOT NULL,
    library_section_id INTEGER REFERENCES library_section (id),
    position INTEGER NOT NULL,
    title TEXT NOT NULL,
    image_renditions TEXT,
    type TEXT NOT NULL,
    uri TEXT,
    language_id INTEGER NOT NULL,
    UNIQUE (external_id)
    );
        CREATE INDEX library_collection_library_section_idx ON library_collection (library_section_id);
        CREATE INDEX library_collection_uri_idx ON library_collection (uri);
        CREATE INDEX library_collection_language_id_idx ON library_collection (language_id);

CREATE TABLE library_section (
    id INTEGER NOT NULL PRIMARY KEY,
    external_id TEXT NOT NULL,
    library_collection_id INTEGER NOT NULL,
    position INTEGER NOT NULL,
    title TEXT,
    index_title TEXT,
    UNIQUE (external_id)
    );
        CREATE INDEX library_section_library_collection_idx ON library_section (library_collection_id);

CREATE TABLE library_item (
    id INTEGER NOT NULL PRIMARY KEY,
    external_id TEXT NOT NULL,
    library_section_id INTEGER REFERENCES library_section (id),
    position INTEGER NOT NULL,
    title TEXT NOT NULL,
    item_id TEXT NOT NULL,
    uri TEXT NOT NULL,
    UNIQUE (external_id)
    );
        CREATE INDEX library_item_item_id_idx ON library_item (item_id);
        CREATE INDEX library_item_library_section_id_idx ON library_item (library_section_id);
        CREATE INDEX library_item_uri_idx ON library_item (uri);

CREATE VIEW library_collection_with_section AS
    SELECT library_collection.*, library_section.title AS section_title, library_section.library_collection_id AS parent_library_collection_id
    FROM library_collection LEFT JOIN library_section ON library_section.id = library_collection.library_section_id;

CREATE VIEW library_item_with_section AS
    SELECT library_item.*, library_section.title AS section, library_section.library_collection_id, item.is_obsolete
    FROM library_item  LEFT JOIN library_section ON library_section.id = library_item.library_section_id LEFT JOIN ITEM ON library_item.item_id = item.id;

CREATE TABLE item (
    id TEXT PRIMARY KEY,
    external_id TEXT NOT NULL,
    language_id INTEGER NOT NULL,
    source_id INTEGER NOT NULL,
    uri TEXT NOT NULL,
    title TEXT NOT NULL,
    image_renditions TEXT,
    item_category_id INTEGER NOT NULL,
    version INTEGER NOT NULL,
    is_obsolete INTEGER NOT NULL,
    type TEXT NOT NULL,
    UNIQUE (external_id)
    );
        CREATE INDEX item_external_id_idx ON item (external_id);
        CREATE INDEX item_type_idx ON item (type);
        CREATE INDEX item_language_id_idx ON item (language_id);

CREATE TABLE item_category (
    id INTEGER NOT NULL PRIMARY KEY,
    name TEXT NOT NULL,
    UNIQUE (name)
    );

CREATE TABLE source (
    id INTEGER NOT NULL PRIMARY KEY,
    name TEXT NOT NULL,
    type TEXT NOT NULL,
    UNIQUE (name)
    );

CREATE TABLE stopword (
    language_id INTEGER NOT NULL REFERENCES language (id),
    word TEXT NOT NULL,
    PRIMARY KEY (language_id, word)
    );

CREATE TABLE subitem_metadata (
    item_id TEXT NOT NULL REFERENCES item (id),
    subitem_id TEXT NOT NULL,
    version INTEGER NOT NULL,
    title TEXT,
    subtitle TEXT,
    image_renditions TEXT,
    position INTEGER NOT NULL,
    PRIMARY KEY (item_id, subitem_id)
    );
        CREATE INDEX subitem_metadata_item_id_idx ON subitem_metadata (item_id);
        CREATE INDEX subitem_metadata_subitem_id_idx ON subitem_metadata (subitem_id);
        CREATE INDEX subitem_metadata_position_idx ON subitem_metadata (position);

CREATE TABLE search_goto (
    language_id INTEGER REFERENCES language (id) NOT NULL,
    item_id TEXT REFERENCES item (id) NOT NULL,
    item_position INTEGER NOT NULL,
    nav_section_id INTEGER NOT NULL,
    nav_collection_id INTEGER,
    nav_position INTEGER NOT NULL,
    subitem_id TEXT REFERENCES subitem_metadata (subitem_id),
    title TEXT NOT NULL,
    short_title TEXT,
    chapter_count INTEGER NOT NULL,
    has_verses INTEGER NOT NULL DEFAULT 0
    );
        CREATE INDEX search_goto_language_id_idx ON search_goto (language_id);
        CREATE INDEX search_goto_item_id_idx ON search_goto (item_id);
        CREATE INDEX search_goto_nav_section_idd_idx ON search_goto (nav_section_id);
        CREATE INDEX search_goto_nav_collection_id_idx ON search_goto (nav_collection_id);
        CREATE INDEX search_goto_subitem_id_idx ON search_goto (subitem_id);
        CREATE INDEX search_goto_title_idx ON search_goto (title);
        CREATE INDEX search_goto_short_title_idx ON search_goto (short_title);

CREATE TABLE speaker (
    id INTEGER PRIMARY KEY,
    uri TEXT,
    full_name TEXT NOT NULL,
    family_name TEXT NOT NULL,
    role TEXT,
    seniority INTEGER,
    image_renditions TEXT,
    language_id INTEGER REFERENCES language (id)
    );
        CREATE INDEX speaker_language_id_idx ON speaker (language_id);
        CREATE INDEX speaker_uri_idx ON speaker (uri);
        CREATE INDEX speaker_full_name_idx ON speaker (full_name);
        CREATE INDEX speaker_family_name_idx ON speaker (family_name);

CREATE TABLE speaker_subitem (
    speaker_id INTEGER REFERENCES speaker (id),
    subitem_id TEXT,
    PRIMARY KEY (speaker_id, subitem_id)
    );

CREATE TABLE topic (
    id INTEGER PRIMARY KEY,
    name TEXT NOT NULL COLLATE NOCASE,
    language_id INTEGER REFERENCES language (id)
    );
        CREATE INDEX topic_name_idx ON topic (name);
        CREATE INDEX topic_language_id_idx ON topic (language_id);

CREATE TABLE topic_subitem (
    topic_id INTEGER REFERENCES topic (id),
    subitem_id TEXT,
    PRIMARY KEY (topic_id, subitem_id)
    );
