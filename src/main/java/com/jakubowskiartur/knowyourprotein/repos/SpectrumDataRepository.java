package com.jakubowskiartur.knowyourprotein.repos;

import com.jakubowskiartur.knowyourprotein.pojos.SpectrumData;
import com.jakubowskiartur.knowyourprotein.pojos.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SpectrumDataRepository extends MongoRepository<SpectrumData, UUID> {

    List<SpectrumData> getAllByUser_Id(String user_id);

    List<SpectrumData> getAllByUser(User user);
}
