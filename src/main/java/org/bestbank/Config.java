//package org.bestbank;
//
//import org.bestbank.repository.ClientSpringDataJpaRepository;
//import org.bestbank.repository.entity.Client;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.domain.Example;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//
//import java.util.List;
//import java.util.Optional;
//
//@Configuration
//public class Config {
//
//
//    @Bean(name = "Repository")
//    public ClientSpringDataJpaRepository clientSpringDataJpaRepository () {
//        return new ClientSpringDataJpaRepository() {
//            @Override
//            public List<Client> findAll() {
//                return null;
//            }
//
//            @Override
//            public List<Client> findAll(Sort sort) {
//                return null;
//            }
//
//            @Override
//            public Page<Client> findAll(Pageable pageable) {
//                return null;
//            }
//
//            @Override
//            public List<Client> findAllById(Iterable<Long> longs) {
//                return null;
//            }
//
//            @Override
//            public long count() {
//                return 0;
//            }
//
//            @Override
//            public void deleteById(Long aLong) {
//
//            }
//
//            @Override
//            public void delete(Client entity) {
//
//            }
//
//            @Override
//            public void deleteAll(Iterable<? extends Client> entities) {
//
//            }
//
//            @Override
//            public void deleteAll() {
//
//            }
//
//            @Override
//            public <S extends Client> S save(S entity) {
//                return null;
//            }
//
//            @Override
//            public <S extends Client> List<S> saveAll(Iterable<S> entities) {
//                return null;
//            }
//
//            @Override
//            public Optional<Client> findById(Long aLong) {
//                return Optional.empty();
//            }
//
//            @Override
//            public boolean existsById(Long aLong) {
//                return false;
//            }
//
//            @Override
//            public void flush() {
//
//            }
//
//            @Override
//            public <S extends Client> S saveAndFlush(S entity) {
//                return null;
//            }
//
//            @Override
//            public void deleteInBatch(Iterable<Client> entities) {
//
//            }
//
//            @Override
//            public void deleteAllInBatch() {
//
//            }
//
//            @Override
//            public Client getOne(Long aLong) {
//                return null;
//            }
//
//            @Override
//            public <S extends Client> Optional<S> findOne(Example<S> example) {
//                return Optional.empty();
//            }
//
//            @Override
//            public <S extends Client> List<S> findAll(Example<S> example) {
//                return null;
//            }
//
//            @Override
//            public <S extends Client> List<S> findAll(Example<S> example, Sort sort) {
//                return null;
//            }
//
//            @Override
//            public <S extends Client> Page<S> findAll(Example<S> example, Pageable pageable) {
//                return null;
//            }
//
//            @Override
//            public <S extends Client> long count(Example<S> example) {
//                return 0;
//            }
//
//            @Override
//            public <S extends Client> boolean exists(Example<S> example) {
//                return false;
//            }
//
//            @Override
//            public Client findByEmail(String email) {
//                return null;
//            }
//        };
//    }
//
//}