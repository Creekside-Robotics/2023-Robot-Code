package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Indexer;

public class SetIndexerMode extends InstantCommand {
    private final Indexer indexer;
    private final Indexer.Mode mode;

    public SetIndexerMode(Indexer indexer, Indexer.Mode mode){
        this.indexer = indexer;
        this.mode = mode;
        addRequirements(this.indexer);
    }

    @Override
    public void initialize() {
        this.indexer.setMode(mode);
    }
}
