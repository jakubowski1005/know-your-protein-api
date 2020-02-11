package com.jakubowskiartur.knowyourprotein.repos;

import com.jakubowskiartur.knowyourprotein.pojos.SpectrumData;
import com.jakubowskiartur.knowyourprotein.pojos.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpectrumDataRepository extends JpaRepository<SpectrumData, Long> {

    List<SpectrumData> getAllByUser_Id(Long user_id);

    List<SpectrumData> getAllByUser(User user);
}
