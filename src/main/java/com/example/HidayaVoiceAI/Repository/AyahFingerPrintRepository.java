package com.example.HidayaVoiceAI.Repository;

import com.example.HidayaVoiceAI.model.AyahFingerPrint;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AyahFingerPrintRepository extends MongoRepository<AyahFingerPrint, String> {


}
