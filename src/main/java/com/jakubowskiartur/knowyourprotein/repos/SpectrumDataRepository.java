package com.jakubowskiartur.knowyourprotein.repos;

import com.jakubowskiartur.knowyourprotein.pojos.SpectrumData;
import com.jakubowskiartur.knowyourprotein.pojos.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SpectrumDataRepository extends JpaRepository<SpectrumData, UUID> {

    List<SpectrumData> getAllByUser_Id(UUID id);

    List<SpectrumData> getAllByUser(User user);
}
