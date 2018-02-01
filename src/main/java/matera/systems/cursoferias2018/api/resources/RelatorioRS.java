package matera.systems.cursoferias2018.api.resources;

import java.text.ParseException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import matera.systems.cursoferias2018.api.domain.DateRange;
import matera.systems.cursoferias2018.api.domain.response.RelatorioResponse;
import matera.systems.cursoferias2018.api.exceptions.AlunoNotFound;
import matera.systems.cursoferias2018.api.exceptions.DisciplinaNotFound;
import matera.systems.cursoferias2018.api.services.RelatorioService;

@RestController
@RequestMapping(path = "/api/v1/relatorio")
public class RelatorioRS {

    @Autowired
    private RelatorioService relatorioService;

    @GetMapping(path = "{disciplinaId}")
    public ResponseEntity<RelatorioResponse> frequenciaByDisciplina(
            @PathVariable String disciplinaId,
            @RequestParam("dataInicio") String dataInicio,
            @RequestParam("dataFim") String dataFim
    ) throws Exception {


        final DateRange range;
        try {
            range = parseDateRange(dataInicio, dataFim);
        } catch (ParseException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Reason", "DataInicio/DataFim malformed [DD-MM-YYYY]").build();
        }

        try {
            RelatorioResponse relatorio = relatorioService.frequenciaByDisciplina(UUID.fromString(disciplinaId), range);
            return ResponseEntity.status(HttpStatus.OK).body(relatorio);
        } catch (DisciplinaNotFound ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @GetMapping(path = "{disciplinaId}/{alunoId}")
    public ResponseEntity<RelatorioResponse> frequenciaByDisciplinaAndAluno(
            @PathVariable String disciplinaId,
            @PathVariable String alunoId,
            @RequestParam("dataInicio") String dataInicio,
            @RequestParam("dataFim") String dataFim
    ) throws Exception {

        final DateRange range;
        try {
            range = parseDateRange(dataInicio, dataFim);
        } catch (ParseException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Reason", "DataInicio/DataFim malformed [DD-MM-YYYY]").build();
        }

        try {

            RelatorioResponse relatorio = relatorioService.frequenciaByDisciplinaAndAluno(UUID.fromString(disciplinaId), UUID.fromString(alunoId), range);
            return ResponseEntity.status(HttpStatus.OK).body(relatorio);

        } catch (DisciplinaNotFound | AlunoNotFound ex) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    /**
     * Parse date range
     * 
     * @param dataInicio
     * @param dataFim
     * @return
     * @throws Exception
     */
    private DateRange parseDateRange(String dataInicio, String dataFim) throws Exception {

        final DateRange range;
        if (StringUtils.isEmpty(dataInicio)) {
            if (StringUtils.isEmpty(dataFim)) {
                range = DateRange.wholePeriod();
            } else {
                range = DateRange.until(dataFim);
            }
        } else {
            if (StringUtils.isEmpty(dataFim)) {
                range = DateRange.since(dataInicio);
            } else {
                range = DateRange.create(dataInicio, dataFim);
            }
        }
        return range;
    }

}
