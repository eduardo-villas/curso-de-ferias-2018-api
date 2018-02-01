package matera.systems.cursoferias2018.api.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import matera.systems.cursoferias2018.api.domain.entity.UsuarioEntity;

@Service
@Profile("stub")
public class UsuarioRepositoryStub implements UsuarioRepository {

    public static final UUID USUARIO_1 = UUID.fromString("3480ed0e-2c8d-4a69-a8ed-7a2f136c4c20");
    public static final UUID USUARIO_2 = UUID.fromString("bc51c8bb-bad3-47e4-af0c-7f467148f23d");
    public static final UUID USUARIO_3 = UUID.fromString("4a8975d1-9e37-4872-bd35-1050707484f8");
    public static final UUID USUARIO_CURSO_DE_FERIAS = UUID.fromString("4a8975d1-9e37-4872-bd35-1050707484f8");

    private static final Map<UUID, UsuarioEntity> data = new HashMap<>();

    static {
        {
            UsuarioEntity entity = new UsuarioEntity();
            entity.setUuid(USUARIO_1);
            entity.setEmail("usuario@domain.com");
            entity.setLogin("usuario");
            entity.setNome("Usuario Um");
            entity.setPerfil("USUARIO");
            entity.setSenha("password");
            entity.setUrlPhoto("http://bucket/usuario/1/perfil.png");
            data.put(USUARIO_1, entity);
        }

        {
            UsuarioEntity entity = new UsuarioEntity();
            entity.setUuid(USUARIO_2);
            entity.setEmail("usuario_2@domain.com");
            entity.setLogin("usuario_2");
            entity.setNome("Usuario Dois");
            entity.setPerfil("ADMINISTRADOR");
            entity.setSenha("senha");
            entity.setUrlPhoto("http://bucket/usuario/2/perfil.png");
            data.put(USUARIO_2, entity);
        }

        {
            UsuarioEntity entity = new UsuarioEntity();
            entity.setUuid(USUARIO_3);
            entity.setEmail("usuario_3@domain.com");
            entity.setLogin("usuario_3");
            entity.setNome("Usuario Tres");
            entity.setPerfil("USUARIO");
            entity.setSenha("senha");
            entity.setUrlPhoto("http://bucket/usuario/3/perfil.png");
            data.put(USUARIO_3, entity);
        }
        
        {
            UsuarioEntity entity = new UsuarioEntity();
            entity.setUuid(USUARIO_CURSO_DE_FERIAS);
            entity.setEmail("cursodeferias@domain.com");
            entity.setLogin("cursodeferias");
            entity.setNome("Curso De Ferias");
            entity.setPerfil("USUARIO");
            entity.setSenha("senhadocurso");
            entity.setUrlPhoto("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSQunOj2zPbnGjtcG2O-8g4XxF3LjEEcgL1FCYsI-rEy1BsDhdEEA");
            data.put(USUARIO_CURSO_DE_FERIAS, entity);
        }
    }

    @Override
    public UUID criar(UsuarioEntity usuario) {
        UUID uuid = UUID.randomUUID();
        data.put(uuid, usuario);
        return uuid;
    }

    @Override
    public void deletar(UUID uuid) {
        data.remove(uuid);
    }

    @Override
    public List<UsuarioEntity> listar() {
        return new ArrayList<>(data.values());
    }

    @Override
    public Optional<UsuarioEntity> findByID(UUID uuid) {
        return Optional.ofNullable(data.getOrDefault(uuid, null));
    }

    @Override
    public void atualizar(UsuarioEntity update) {
        data.put(update.getUuid(), update);
    }

    @Override
    public Optional<UsuarioEntity> findByUsuario(String usuarioLogin) {
		return data.entrySet()
    				.stream()
    				.map(Entry::getValue)
    				.filter(user -> Objects.equals(usuarioLogin, user.getLogin()))
    				.findFirst()
    				.flatMap(Optional::ofNullable);
    }
}
