create table albums
(
    album_id            bigint                                  not null
        primary key,
    album_name          varchar(255) collate utf8mb4_unicode_ci not null,
    album_tracklist_url longtext                                not null,
    photoUrl            longtext                                null
);

create table artists
(
    artist_id            bigint                                  not null
        primary key,
    artist_name          varchar(255) collate utf8mb4_unicode_ci not null,
    artist_tracklist_url longtext                                not null,
    photoUrl             longtext                                null
);

create table genres
(
    genre_id   int auto_increment
        primary key,
    genre_name varchar(20) not null,
    constraint genres_pk2
        unique (genre_name)
);

create table roles
(
    role_id   int auto_increment
        primary key,
    role_name varchar(30) not null,
    constraint roles_pk2
        unique (role_name)
);

create table tracks
(
    track_id    bigint                                  not null
        primary key,
    track_title varchar(255) collate utf8mb4_unicode_ci not null,
    preview_url longtext                                not null,
    playtime    double default 0                        not null,
    artist_id   bigint                                  not null,
    album_id    bigint                                  not null,
    `rank`      bigint default 0                        not null,
    genre_id    int                                     not null,
    constraint tracks_albums_album_id_fk
        foreign key (album_id) references albums (album_id),
    constraint tracks_artists_artist_id_fk
        foreign key (artist_id) references artists (artist_id),
    constraint tracks_genres_genre_id_fk
        foreign key (genre_id) references genres (genre_id)
);

create table users
(
    user_id         int auto_increment,
    username        varchar(16) null,
    password        text        null,
    first_name      varchar(22) null,
    last_name       varchar(22) null,
    email           varchar(40) not null,
    profile_picture longtext    null,
    provider        varchar(15) null,
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
    `rank`         bigint default 0 not null,
    user_id        int              not null,
    constraint playlists_users_user_id_fk
        foreign key (user_id) references users (user_id)
);

create table playlists_genres
(
    playlist_id int not null,
    genre_id    int not null,
    constraint playlists_genres_id_fk
        foreign key (genre_id) references genres (genre_id),
    constraint playlists_playlists_playlist_id_fk
        foreign key (playlist_id) references playlists (playlist_id)
);

create table playlists_tracks
(
    playlist_id int    not null,
    track_id    bigint not null,
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

