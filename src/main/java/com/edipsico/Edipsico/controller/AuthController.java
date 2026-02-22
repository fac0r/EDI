package com.edipsico.Edipsico.controller;

import com.edipsico.Edipsico.model.Profesional;
import com.edipsico.Edipsico.repository.ProfesionalRepository;
import com.edipsico.Edipsico.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final ProfesionalRepository profesionalRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(ProfesionalRepository profesionalRepository,
                          PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil) {
        this.profesionalRepository = profesionalRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        Optional<Profesional> profesionalOpt = profesionalRepository.findByEmail(email);

        if (profesionalOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Credenciales inválidas"));
        }

        Profesional profesional = profesionalOpt.get();

        if (!passwordEncoder.matches(password, profesional.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Credenciales inválidas"));
        }

        String token = jwtUtil.generateToken(profesional.getEmail(), profesional.getRol().name());

        return ResponseEntity.ok(Map.of(
                "token", token,
                "rol", profesional.getRol().name(),
                "nombre", profesional.getNombre(),
                "apellido", profesional.getApellido()
        ));
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String email = jwtUtil.extractEmail(token);
        return profesionalRepository.findByEmail(email)
                .map(p -> ResponseEntity.ok(Map.of(
                        "id", p.getId(),
                        "nombre", p.getNombre(),
                        "apellido", p.getApellido(),
                        "rol", p.getRol().name(),
                        "email", p.getEmail()
                )))
                .orElse(ResponseEntity.notFound().build());
    }


}