package com.example.BC_Revision.controller.rest;

import com.example.BC_Revision.dto.LoginRequest;
import com.example.BC_Revision.dto.UtilisateurDto;
import com.example.BC_Revision.mapper.UtilisateurMapper;
import com.example.BC_Revision.model.Utilisateur;
import com.example.BC_Revision.service.UtilisateurService;
import com.example.BC_Revision.service.impl.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@Validated
@RequestMapping("/utilisateurs")
public class UtilisateurControllerRest {
    private UtilisateurService utilisateurService;
    private AuthenticationManager authenticationManager;
    private TokenService tokenService;
    private PasswordEncoder passwordEncoder;
    private UtilisateurMapper utilisateurMapper;
    private final ObjectMapper objectMapper; // Ajoutez un ObjectMapper

    private final Path rootLocation = Paths.get("C:/Users/emeli/Desktop/CDA/Cours/Business Case/ElectricityBusiness_Java/upload");

    public UtilisateurControllerRest(UtilisateurService utilisateurService, TokenService tokenService,
                                     PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
                                     UtilisateurMapper utilisateurMapper){

        this.utilisateurService = utilisateurService;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.utilisateurMapper = utilisateurMapper;
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    //Méthode Get #1
    @GetMapping("")
    public List<UtilisateurDto> getAllUtilisateurs() {

        return utilisateurService.getAllUtilisateurs();
    }

    //Méthode Get #2
    @GetMapping("/{id}")
    @ResponseStatus(code= HttpStatus.OK)
    public UtilisateurDto getUtilisateurById(@PathVariable Long id) {
        return utilisateurService.getUtilisateurById(id);
    }

    //Méthode Post
    @PostMapping("")
    @ResponseStatus(code=HttpStatus.CREATED)
    public Utilisateur saveUtilisateur(@Valid @RequestBody UtilisateurDto utilisateurDto,
                                       BindingResult result) {
        utilisateurDto.setMotDePasse(passwordEncoder.encode(utilisateurDto.getMotDePasse()));
        return utilisateurService
                .saveUtilisateur(utilisateurDto);
    }

    //Méthode Delete
    @DeleteMapping("/{id}")
    @ResponseStatus(code=HttpStatus.NO_CONTENT)
    public void deleteUtilisateur(@PathVariable Long id) {

        utilisateurService.deleteUtilisateur(id);
    }


    @Operation(summary = "Modifier l'utilisateur", description ="Modifier un utilisateur et éventuellement sa photo")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UtilisateurDto updateUtilisateur(@PathVariable Long id,
                                            @Valid @RequestParam ("utilisateurDto") String utilisateurDtoJSON,
                                            @RequestPart(name ="file", required = false) MultipartFile file) throws Exception {

        // 1. Désérialiser la chaîne JSON en objet UtilisateurDto
        UtilisateurDto utilisateurDto = objectMapper.readValue(utilisateurDtoJSON, UtilisateurDto.class);

        // 2. Définir l'ID sur l'objet DTO désérialisé (important pour la mise à jour)
        utilisateurDto.setId(id);

        Utilisateur savedUser;

        // 3. Gérer la mise à jour avec ou sans fichier
        if (file != null && !file.isEmpty()) {
            // Logique pour sauvegarder le fichier
            String nomFichier = UUID.randomUUID() + "_" + file.getOriginalFilename(); // Ajout d'un _ pour la lisibilité
            Path destinationFile = this.rootLocation.resolve(Paths.get(nomFichier)).normalize().toAbsolutePath();

            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new Exception("Cannot store file outside current directory.");
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new Exception("Failed to store file.", e);
            }
            utilisateurDto.setPhotoProfil(nomFichier); // Mettre à jour le chemin de la photo dans le DTO
            // Appeler une méthode de service spécifique pour la mise à jour avec photo
            savedUser = utilisateurService.updateUtilisateur(utilisateurDto); // Vous devrez adapter votre service pour gérer la photo
        } else {
            // Appeler une méthode de service pour la mise à jour sans photo
            savedUser = utilisateurService.updateUtilisateur(utilisateurDto);
        }

        return utilisateurMapper.toDto(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authenticationObject = new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getEmail()); // Correction ici, il faut utiliser l'email pour le password pour l'authentification basée sur email

            String token = tokenService.generateToken(authenticationObject);

            //Créer un objet JSON avec le token
            Map<String, String> response = new HashMap<>();
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/current-user")
    public UtilisateurDto getUtilisateurCourant(@AuthenticationPrincipal UserDetails userDetails) {
        return utilisateurService.getUtilisateurByEmail(userDetails.getUsername());
    }

    @GetMapping("/upload/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        // Assurez-vous que le chemin est correct et sécurisé pour l'accès aux fichiers
        Path file = rootLocation.resolve(filename).normalize();
        if (!Files.exists(file) || !Files.isReadable(file)) {
            return ResponseEntity.notFound().build();
        }
        Resource resource = new FileSystemResource(file);
        String contentType;
        try {
            contentType = Files.probeContentType(file);
        } catch (IOException e) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType)) // Détermine dynamiquement le type de contenu
                .body(resource);
    }

    @Operation(summary = "Créer un nouvelutilisateur", description = "Créer un nouvel utilisateur avec sa borne existante ou non")
    @PostMapping(value = "/user/bornes", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public UtilisateurDto saveUtilisateur(
            @Valid @RequestParam("utilisateurDto") String utilisateurDTOJson,
            @RequestPart("file") MultipartFile file) throws Exception {

        // ObjectMapper mapper = new ObjectMapper(); // Utilisez l'ObjectMapper de la classe
        UtilisateurDto utilisateurDto = objectMapper.readValue(utilisateurDTOJson, UtilisateurDto.class);

        // Correction: Supprimez cette vérification si ReservationsId et BornesId peuvent être nuls pour la création
        // ou adaptez-la à votre logique métier. Si c'est pour lier à des bornes existantes, cette logique est peut-être ailleurs.
        // if (utilisateurDto.getReservationsId() == null || utilisateurDto.getBornesId() == null) {
        //     throw new IllegalArgumentException("Reservation ID et Borne ID sont obligatoires.");
        // }

        if (file.isEmpty()) {
            throw new Exception("Failed to store empty file.");
        }

        String nomFichier = UUID.randomUUID() + "_" + file.getOriginalFilename(); // Ajout d'un _ pour la lisibilité
        Path destinationFile = this.rootLocation.resolve(
                        Paths.get(nomFichier))
                .normalize().toAbsolutePath();


        if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
            throw new Exception("Cannot store file outside current directory.");
        }

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new Exception("Failed to store file.", e);
        }

        utilisateurDto.setPhotoProfil(nomFichier);
        utilisateurDto.setMotDePasse(passwordEncoder.encode(utilisateurDto.getMotDePasse())); // Encodez le mot de passe avant de sauvegarder
        Utilisateur utilisateur = utilisateurService.saveUtilisateur(utilisateurDto);
        return this.utilisateurMapper.toDto(utilisateur);
    }

    // Note: Vous avez deux méthodes `saveUtilisateur` avec des signatures différentes.
    // Il est généralement préférable d'utiliser des noms de méthodes plus spécifiques
    // ou de fusionner les logiques si elles sont similaires. La méthode avec @RequestBody
    // est pour un utilisateur sans photo, et celle avec @RequestParam("utilisateurDto")
    // et @RequestPart("file") est pour un utilisateur avec photo. Assurez-vous que c'est
    // votre intention.

    // @ExceptionHandler(BorneInexistanteException.class)
    // @ResponseStatus(code=HttpStatus.NOT_FOUND)
    // public String traiterBorneInexistanteException(Exception e) {
    //     return e.getMessage();
    // }

    // @ExceptionHandler(ParasolDejaExistantException.class)
    // @ResponseStatus(code=HttpStatus.CONFLICT)
    // public String traiterParasolDejaPresentException(Exception e) {
    //     return e.getMessage();
    // }
}