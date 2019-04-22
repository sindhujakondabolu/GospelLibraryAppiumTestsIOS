CREATE TABLE metadata (
    key TEXT PRIMARY KEY,
    value TEXT NOT NULL
    );

CREATE TABLE subitem (
    id TEXT PRIMARY KEY,
    uri TEXT NOT NULL,
    position INTEGER NOT NULL,
    title TEXT NOT NULL,
    version INTEGER NOT NULL,
    type TEXT NOT NULL,
    UNIQUE(uri),
    UNIQUE(position)
    );
        CREATE INDEX subitem_uri_idx ON subitem (uri);

CREATE TABLE subitem_content (
    subitem_id TEXT PRIMARY KEY,
    content_html TEXT NOT NULL
    );

CREATE TABLE related_content_item (
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    subitem_id TEXT NOT NULL REFERENCES subitem(subitem_id),
    ref_id TEXT NOT NULL,
    label_html TEXT NOT NULL,
    origin_id TEXT NOT NULL,
    content_html TEXT NOT NULL,
    word_offset INTEGER NOT NULL,
    byte_location INTEGER NOT NULL,
    UNIQUE(subitem_id, byte_location)
    );
        CREATE INDEX related_content_item_subitem_idx ON related_content_item (subitem_id);

CREATE TABLE related_audio_item (
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    subitem_id TEXT NOT NULL REFERENCES subitem(subitem_id),
    audio_id TEXT NOT NULL,
    media_url TEXT NOT NULL,
    file_size INTEGER NOT NULL,
    duration INTEGER NOT NULL,
    voice TEXT
    );
        CREATE INDEX related_audio_item_subitem_idx ON related_audio_item (subitem_id);
        CREATE INDEX related_audio_item_audio_id_idx ON related_audio_item (audio_id);

CREATE TABLE related_video_item (
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    subitem_id TEXT NOT NULL REFERENCES subitem(subitem_id),
    poster_url TEXT,
    video_id TEXT NOT NULL,
    title TEXT NOT NULL
    );
        CREATE INDEX related_video_item_subitem_idx ON related_video_item (subitem_id);

CREATE TABLE related_video_item_source (
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    media_url TEXT NOT NULL,
    type TEXT NOT NULL,
    width INTEGER,
    height INTEGER,
    file_size INTEGER,
    related_video_item_id INTEGER REFERENCES related_video_item(id)
    );
        CREATE INDEX related_video_item_source_related_video_item_id_idx ON related_video_item_source (related_video_item_id);

CREATE TABLE nav_collection (
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    nav_section_id INTEGER,
    position INTEGER NOT NULL,
    title TEXT NOT NULL,
    subtitle TEXT,
    uri TEXT NOT NULL,
    type TEXT NOT NULL,
    UNIQUE(nav_section_id, position),
    UNIQUE(uri)
    );
        CREATE INDEX nav_collection_nav_section_idx ON nav_collection (nav_section_id);

CREATE TABLE nav_collection_index_entry (
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    nav_collection_id INTEGER NOT NULL,
    position INTEGER NOT NULL,
    title TEXT NOT NULL,
    list_index INTEGER NOT NULL,
    section INTEGER NOT NULL,
    row INTEGER NOT NULL,
    UNIQUE(nav_collection_id, position)
    );
        CREATE INDEX nav_collection_index_entry_nav_collection_idx ON nav_collection_index_entry (nav_collection_id);

CREATE TABLE nav_section (
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    nav_collection_id INTEGER NOT NULL,
    position INTEGER NOT NULL,
    indent_level INTEGER NOT NULL,
    title TEXT,
    is_collation INTEGER NOT NULL,
    UNIQUE(nav_collection_id, position)
    );
        CREATE INDEX nav_section_nav_collection_idx ON nav_section (nav_collection_id);

CREATE TABLE nav_item (
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    nav_section_id INTEGER NOT NULL,
    position INTEGER NOT NULL,
    image_renditions TEXT,
    collation_title TEXT,
    short_title TEXT,
    title TEXT NOT NULL,
    primary_title TEXT NOT NULL,
    title_number TEXT,
    subtitle TEXT,
    preview TEXT,
    uri TEXT NOT NULL,
    subitem_id TEXT NOT NULL REFERENCES subitem(subitem_id),
    start_date DATE,
    end_date DATE,
    UNIQUE(nav_section_id, position),
    UNIQUE(uri)
    );
        CREATE INDEX nav_item_uri_idx ON nav_item (uri);
        CREATE INDEX nav_item_nav_section_idx ON nav_item (nav_section_id);
        CREATE INDEX nav_item_subitem_id_idx ON nav_item (subitem_id);

CREATE TABLE paragraph_metadata (
    subitem_id TEXT NOT NULL REFERENCES subitem(subitem_id),
    paragraph_id TEXT NOT NULL,
    paragraph_aid TEXT NOT NULL,
    verse_number TEXT,
    start_index INTEGER NOT NULL,
    end_index INTEGER NOT NULL,
    PRIMARY KEY(subitem_id, paragraph_id)
    );
        CREATE INDEX paragraph_metadata_paragraph_aid_idx ON paragraph_metadata (paragraph_aid);
