package com.example.ridepal.services;

import com.example.ridepal.exceptions.EntityNotFoundException;
import com.example.ridepal.models.Album;
import com.example.ridepal.repositories.AlbumRepository;
import com.example.ridepal.services.contracts.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;

    @Autowired
    public AlbumServiceImpl(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @Override
    public List<Album> findAll() {
        return albumRepository.findAll();
    }

    @Override
    public Album findById(int id) {
        if(albumRepository.findById(id)==null){
            throw new EntityNotFoundException("Album", id);
        }else {
            return albumRepository.findById(id);
        }
    }

    @Override
    public void create(Album album) {
        albumRepository.create(album);
    }
}
