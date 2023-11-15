create table roles
(
    role_id   int auto_increment
        primary key,
    role_name varchar(30) not null,
    constraint roles_pk2
        unique (role_name)
);

create table tags
(
    tag_id   int auto_increment
        primary key,
    tag_name varchar(20) not null,
    constraint tags_pk2
        unique (tag_name)
);

create table tracks
(
    track_id    int         not null
        primary key,
    artist      varchar(30) not null,
    track_title varchar(60) not null,
    album       varchar(30) not null,
    playtime    time        not null,
    `rank`      double      not null,
    preview_url longtext    not null
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
    title          varchar(30) not null,
    total_playtime time        not null,
    `rank`         double      not null,
    user_id        int         not null,
    constraint playlists_users_user_id_fk
        foreign key (user_id) references users (user_id)
);

create table playlists_tags
(
    playlist_id int not null,
    tag_id      int not null,
    constraint playlists_tags_playlists_playlist_id_fk
        foreign key (playlist_id) references playlists (playlist_id),
    constraint playlists_tags_tags_tag_id_fk
        foreign key (tag_id) references tags (tag_id)
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


