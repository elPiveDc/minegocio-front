package com.minegocio.backend.repository;

import com.minegocio.backend.model.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    Optional<Invitation> findByToken(String token);

    List<Invitation> findByInvitedEmail(String invitedEmail);

    List<Invitation> findByFranquiciaId(Integer idFranquicia);
}
