package matera.systems.cursoferias2018.api.services;

import matera.systems.cursoferias2018.api.repository.FrequenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class FrequenciaService {

    @Autowired
    private FrequenciaRepository repository;

    public void incluirPresenca(UUID disicplina, UUID aluno, Date data) {

        repository.incluir(disicplina, aluno, data);
    }

    public void removerPresenca(UUID disicplina, UUID aluno, Date data) {

        repository.remover(disicplina, aluno, data);
    }

    public List<String> findFrequenciaByAlunoId(UUID aluno) {
        return new ArrayList<>();
    }

}
