create table albums
(
    album_id            int auto_increment
        primary key,
    album_name          varchar(150) not null,
    album_tracklist_url longtext    not null
);

create table artists
(
    artist_id            int auto_increment
        primary key,
    artist_name          varchar(100) not null,
    artist_tracklist_url longtext    not null
);

create table roles
(
    role_id   int auto_increment
        primary key,
    role_name varchar(30) not null,
    constraint roles_pk2
        unique (role_name)
);

create table genres
(
    genre_id   int auto_increment
        primary key,
    genre_name varchar(20) not null,
    constraint genres_pk2
        unique (genre_name)
);

create table tracks
(
    track_id    int auto_increment  not null
        primary key,
    track_title varchar(150)      not null,
    preview_url longtext         not null,
    playtime    double default 0 not null,
    artist_id   int              not null,
    album_id    int              not null,
    `rank`      double default 0 not null,

    constraint tracks_albums_album_id_fk
        foreign key (album_id) references albums (album_id),
    constraint tracks_artists_artist_id_fk
        foreign key (artist_id) references artists (artist_id)

);

create table tracks_genres
(
    track_id int not null,
    genre_id   int not null,
    constraint tracks_genres_id_fk
        foreign key (genre_id) references genres (genre_id),
    constraint tracks_tags_tracks_track_id_fk
        foreign key (track_id) references tracks (track_id)
);

create table users
(
    user_id         int auto_increment,
    username        varchar(16) not null,
    password        varchar(50) not null,
    first_name      varchar(22) not null,
    last_name       varchar(22) not null,
    email           varchar(40) not null,
    profile_picture longtext    null,
    constraint users_pk
        unique (user_id),
    constraint users_pk2
        unique (username),
    constraint users_pk3
        unique (email)
);

create table playlists
(
    playlist_id    int auto_increment
        primary key,
    title          varchar(30)      not null,
    total_playtime double default 0 not null,
    `rank`         double default 0 not null,
    user_id        int              not null,
    constraint playlists_users_user_id_fk
        foreign key (user_id) references users (user_id)
);

create table playlists_genres
(
    playlist_id int not null,
    genre_id      int not null,
    constraint playlists_playlists_playlist_id_fk
        foreign key (playlist_id) references playlists (playlist_id),
    constraint playlists_genres_id_fk
        foreign key (genre_id) references genres (genre_id)
);

create table playlists_tracks
(
    playlist_id int not null,
    track_id    int not null,
    constraint playlists_tracks_playlists_playlist_id_fk
        foreign key (playlist_id) references playlists (playlist_id),
    constraint playlists_tracks_tracks_track_id_fk
        foreign key (track_id) references tracks (track_id)
);

create table users_roles
(
    user_id int not null,
    role_id int not null,
    constraint users_roles_roles_role_id_fk
        foreign key (role_id) references roles (role_id),
    constraint users_roles_users_user_id_fk
        foreign key (user_id) references users (user_id)
);



