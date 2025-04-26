package com.henriquebarucco.movielie.database.mongodb.repository

import com.henriquebarucco.movielie.database.mongodb.document.MovieDocument
import org.springframework.data.mongodb.repository.MongoRepository

interface MovieMongodbRepository : MongoRepository<MovieDocument, String>
