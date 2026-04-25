package org.estudio.Models.EstrucParametrosPaciente;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.estudio.Models.Reglas.EstadoSintimas;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InfoPersonal {

    private int edad;
    private String nombre;
    private boolean estaAcompanado;
    private boolean enEmbarazo;
    private boolean menorDeEdad;
    private boolean adultoMayor;
    private EstadoSintimas estadoSintomas;

    @Builder
    public InfoPersonal(int edad, String nombre, boolean estaAcompanado, boolean enEmbarazo, EstadoSintimas estadoSintomas){
        this.edad = edad;
        this.nombre = nombre;
        this.estaAcompanado = estaAcompanado;
        this.enEmbarazo = enEmbarazo;
        this.estadoSintomas = estadoSintomas;

        this.menorDeEdad = this.edad < 18;
        this.adultoMayor = this.edad > 70;
    }

    public int evaluarPrioridad() throws Exception{
        try {
            if (this.enEmbarazo || (this.menorDeEdad && !this.estaAcompanado) || (this.adultoMayor && !this.estaAcompanado)) return 3;
            else if (this.estaAcompanado && (this.estadoSintomas == EstadoSintimas.MODERADO || this.estadoSintomas == EstadoSintimas.GRAVE)) return 2;
            return 0;
        }catch (Exception e){
            throw new Exception(e);
        }
    }


}
